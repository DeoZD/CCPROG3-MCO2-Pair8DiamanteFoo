import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HotelManagementView {

    private JFrame frame;
    private JTextArea displayArea;
    private JPanel panel;
    private JComboBox<String> hotelOptionsComboBox; // added for the option comboBox GUI


    public HotelManagementView() {
        frame = new JFrame();
        frame.setTitle("Hotel Management System");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        frame.getContentPane().add(new JScrollPane(displayArea), BorderLayout.CENTER);

        panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.NORTH);

        JButton btnCreateHotel = new JButton("Create Hotel");
        panel.add(btnCreateHotel);

        JButton btnRemoveHotel = new JButton("Remove Hotel");
        panel.add(btnRemoveHotel);

        JButton btnViewHotel = new JButton("View Hotel");
        panel.add(btnViewHotel);

        JButton btnManageHotel = new JButton("Manage Hotel");
        panel.add(btnManageHotel);

        JButton btnSimulateBooking = new JButton("Booking");
        panel.add(btnSimulateBooking);
    }

    public void setCreateHotelButtonListener(ActionListener actionListener) {
        JButton btnCreateHotel = (JButton) panel.getComponent(0);
        btnCreateHotel.addActionListener(actionListener);
    }

    public void setRemoveHotelButtonListener(ActionListener actionListener) {
        JButton btnRemoveHotel = (JButton) panel.getComponent(1);
        btnRemoveHotel.addActionListener(actionListener);
    }

    public void setViewHotelButtonListener(ActionListener actionListener) {
        JButton btnViewHotel = (JButton) panel.getComponent(2);
        btnViewHotel.addActionListener(actionListener);
    }

    public void setManageHotelButtonListener(ActionListener actionListener) {
        JButton btnManageHotel = (JButton) panel.getComponent(3);
        btnManageHotel.addActionListener(actionListener);
    }

    public void setSimulateBookingButtonListener(ActionListener actionListener) {
        JButton btnSimulateBooking = (JButton) panel.getComponent(4);
        btnSimulateBooking.addActionListener(actionListener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    public void displayInfo(String message) {
        displayArea.append(message + "\n");
    }

    public String getUserInput(String message) {
        return JOptionPane.showInputDialog(frame, message);
    }

    public void displayHotelDetails(String details) {
        displayArea.setText(details);
    }

    

    public void displayHotelOptions(List<Hotel> hotels) { // Pass the list of hotels
        String[] hotelNames = new String[hotels.size()]; // Create an array to hold hotel names
        for (int i = 0; i < hotels.size(); i++) { // Loop through the hotels
            hotelNames[i] = hotels.get(i).getName(); // Add each hotel name to the array
        }
        hotelOptionsComboBox = new JComboBox<>(hotelNames); // Create a JComboBox with the hotel names
        // ... (Add hotelOptionsComboBox to the GUI) 
        // Example: 
        // add(hotelOptionsComboBox); // Add the combobox to your layout 
        // hotelOptionsComboBox.setBounds(100, 100, 200, 25); // Set bounds if needed

        hotelOptionsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==hotelOptionsComboBox) {
                    String selectedHotelName = (String) hotelOptionsComboBox.getSelectedItem();
                    // Now you can use selectedHotelName in your controller 
                }
            }
        });
    }

    public String getSelectedHotelOption() {
        return (String) hotelOptionsComboBox.getSelectedItem(); // Get the selected item as a String
    }

    public void showFrame() {
        frame.setVisible(true);
    }
}
