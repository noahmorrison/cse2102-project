package edu.uconn.cse2102.project.role2;

import edu.uconn.cse2102.project.common.Hospital;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MainMenu extends JFrame
{
    private JPanel cards;
    private CardLayout layout;

    private String state;
    private String city;
    private String address;
    private String address1;
    private String address2;
    private String hospitalType;

    private JLabel addressLabel;
    private JLabel descriptionLabel;
    private JLabel hospitalLabel;


    private static Font normalFont = new Font("Sans Serif", Font.PLAIN, 16);
    private static Font boldFont = new Font("Sans Serif", Font.BOLD, 24);

    public MainMenu()
    {
        int x = 0;
        int y = 0;
        int width = 750;
        int height = 500;
        this.setBounds(x, y, width, height);
        this.getContentPane().setBackground(Color.white);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        cards = new JPanel(new CardLayout());

        JPanel mainCard = makeMainCard();
        JPanel addressCard = makeGetAddressCard();
        JPanel resultsCard = makeResultsCard();

        cards.add(mainCard, "main");
        cards.add(addressCard, "address");
        cards.add(resultsCard, "results");

        layout = (CardLayout)(cards.getLayout());

        this.getContentPane().add(cards);
        this.setVisible(true);
    }

    private JPanel makeMainCard()
    {
        JPanel card = new JPanel(new GridBagLayout());

        JLabel label1 = newLabel("Already have a home you're interested in?", boldFont);
        JLabel label2 = newLabel("Locate nearby hospital with", normalFont);
        JLabel label3 = newLabel("or", normalFont);
        JButton ESBtn = newButton("Emergency Services");
        JButton EHRBtn = newButton("Electronic Health Records");

        ESBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                hospitalType = "Emergency Services";
                layout.show(cards, "address");
            }
        });

        EHRBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                hospitalType = "Electronic Health Records";
                layout.show(cards, "address");
            }
        });

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        card.add(label1, c);
        c.gridy = 1;
        c.gridwidth = 1;
        card.add(label2, c);
        c.gridx = 1;
        card.add(ESBtn, c);
        c.gridx = 2;
        card.add(label3, c);
        c.gridx = 3;
        card.add(EHRBtn, c);

        return card;
    }

    private JPanel makeGetAddressCard()
    {
        JPanel card = new JPanel(new GridBagLayout());

        // Labels
        JLabel statelabel = newLabel("State", normalFont);
        JLabel citylabel = newLabel("City", normalFont);
        JLabel addresslabel = newLabel("Address", normalFont);

        // Fields
        JTextField statefield = newField();
        JTextField cityfield = newField();
        JTextField addressfield1 = newField();
        JTextField addressfield2 = newField();

        // Buttons
        JButton okBtn = newButton("Okay");
        okBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                state = statefield.getText();
                city = cityfield.getText();
                address1 = addressfield1.getText();
                address2 = addressfield2.getText();
                address = address1;
                if (!address2.equals(""))
                {
                    address += " " + address2;
                }

                if (state.equals("") || city.equals("") || address.equals("")) return;

                updateResults();
                layout.show(cards, "results");
            }
        });

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        card.add(statelabel, c);
        c.gridx = 1;
        card.add(statefield, c);

        c.gridy = 1;
        c.gridx = 0;
        card.add(citylabel, c);
        c.gridx = 1;
        card.add(cityfield, c);

        c.gridy = 2;
        c.gridx = 0;
        card.add(addresslabel, c);
        c.gridx = 1;
        card.add(addressfield1, c);
        c.gridy = 3;
        card.add(addressfield2, c);

        c.gridx = 2;
        c.gridy = 4;
        card.add(okBtn, c);

        return card;
    }

    private JPanel makeResultsCard()
    {
        JPanel card = new JPanel(new GridBagLayout());

        JLabel label1 = newLabel("The closest hospital to", normalFont);
        JButton backBtn = newButton("Back to Menu");

        addressLabel = newLabel("", normalFont);
        descriptionLabel = newLabel("", normalFont);
        hospitalLabel = newLabel("", boldFont);

        backBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                layout.show(cards, "main");
            }
        });

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        card.add(label1, c);

        c.gridy = 1;
        card.add(addressLabel, c);

        c.gridy = 2;
        card.add(descriptionLabel, c);

        c.gridy = 3;
        card.add(hospitalLabel, c);

        c.gridy = 4;
        card.add(backBtn, c);

        return card;
    }

    private void updateResults()
    {
        state = Util.expandState(state);

        RouteLength router = new RouteLength(state);
        double[] from = LatLong.get(address, city, state);
        double nearestTime = Double.MAX_VALUE;
        Hospital nearest = null;
        for (Hospital hospital: Hospital.load())
        {
            if (!Util.expandState(hospital.getState()).equals(state)) continue;
            if (hospitalType.equals("Emergency Services") && !hospital.hasEmergencyServices()) continue;
            if (hospitalType.equals("Electronic Health Records") && !hospital.hasEHR()) continue;

            double[] to = LatLong.get(hospital);
            if (to == null) continue;

            double time = router.getTime(from, to);
            if (time < nearestTime)
            {
                nearestTime = time;
                nearest = hospital;
            }

        }

        addressLabel.setText(address + ", " + city + ", " + state);
        descriptionLabel.setText("With " + hospitalType + " is");
        hospitalLabel.setText(nearest.getFullAddress());
    }

    private static JButton newButton(String text)
    {
        JButton button = new JButton(text);
        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE);
        button.setFont(normalFont);

        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);
        return button;
    }

    private static JTextField newField()
    {
        JTextField f = new JTextField("");
        f.setPreferredSize( new Dimension( 200, 24 ) );
        f.setFont(normalFont);
        return f;
    }

    private static JLabel newLabel(String text, Font font)
    {
        JLabel l = new JLabel(text);
        l.setFont(font);
        return l;
    }
}
