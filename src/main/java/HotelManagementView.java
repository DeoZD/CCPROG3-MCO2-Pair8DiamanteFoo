import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * The HotelManagementView class represents the graphical user interface (GUI)
 * for the hotel management system. It provides methods to set listeners for
 * different buttons and display information to the user.
 * 
 * @author James Foo
 * @author Zami Diamante
 * @version 1.0
 */
public class HotelManagementView {

    private JFrame frame;
    private JTextArea displayArea;
    private JPanel panel;
    private JComboBox<String> hotelOptionsComboBox; // ComboBox for displaying hotel options

    /**
     * Constructor for HotelManagementView.
     * Initializes the GUI components and sets up the layout.
     */
    public HotelManagementView() {
        frame = new JFrame();
        frame.setTitle("Hotel Management System");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false); // Make the display area read-only
        frame.getContentPane().add(new JScrollPane(displayArea), BorderLayout.CENTER);

        panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.NORTH);

        // Create and add buttons to the panel
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

    /**
     * Sets the action listener for the "Create Hotel" button.
     *
     * @param actionListener the ActionListener to be set
     */
    public void setCreateHotelButtonListener(ActionListener actionListener) {
        JButton btnCreateHotel = (JButton) panel.getComponent(0);
        btnCreateHotel.addActionListener(actionListener);
    }

    /**
     * Sets the action listener for the "Remove Hotel" button.
     *
     * @param actionListener the ActionListener to be set
     */
    public void setRemoveHotelButtonListener(ActionListener actionListener) {
        JButton btnRemoveHotel = (JButton) panel.getComponent(1);
        btnRemoveHotel.addActionListener(actionListener);
    }

    /**
     * Sets the action listener for the "View Hotel" button.
     *
     * @param actionListener the ActionListener to be set
     */
    public void setViewHotelButtonListener(ActionListener actionListener) {
        JButton btnViewHotel = (JButton) panel.getComponent(2);
        btnViewHotel.addActionListener(actionListener);
    }

    /**
     * Sets the action listener for the "Manage Hotel" button.
     *
     * @param actionListener the ActionListener to be set
     */
    public void setManageHotelButtonListener(ActionListener actionListener) {
        JButton btnManageHotel = (JButton) panel.getComponent(3);
        btnManageHotel.addActionListener(actionListener);
    }

    /**
     * Sets the action listener for the "Simulate Booking" button.
     *
     * @param actionListener the ActionListener to be set
     */
    public void setSimulateBookingButtonListener(ActionListener actionListener) {
        JButton btnSimulateBooking = (JButton) panel.getComponent(4);
        btnSimulateBooking.addActionListener(actionListener);
    }

    /**
     * Displays a message to the user in a dialog box.
     *
     * @param message the message to be displayed
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    /**
     * Appends the given message to the display area.
     *
     * @param message the message to be displayed
     */
    public void displayInfo(String message) {
        displayArea.append(message + "\n");
    }

    /**
     * Prompts the user for input and returns the entered value.
     *
     * @param message the prompt message
     * @return the user's input
     */
    public String getUserInput(String message) {
        return JOptionPane.showInputDialog(frame, message);
    }

    /**
     * Sets the text of the display area to the given details.
     *
     * @param details the details to be displayed
     */
    public void displayHotelDetails(String details) {
        displayArea.setText(details);
    }

    /**
     * Displays a list of hotels in a combo box for selection.
     *
     * @param hotels the list of hotels to be displayed
     */
    public void displayHotelOptions(List<Hotel> hotels) {
        String[] hotelNames = new String[hotels.size()]; // Create an array to hold hotel names
        for (int i = 0; i < hotels.size(); i++) { // Loop through the hotels
            hotelNames[i] = hotels.get(i).getName(); // Add each hotel name to the array
        }

        hotelOptionsComboBox = new JComboBox<>(hotelNames); // Initialize the combo box
        panel.add(hotelOptionsComboBox); // Add the combo box to the panel
        panel.revalidate(); // Refresh the panel to show the new component
        panel.repaint();

        hotelOptionsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == hotelOptionsComboBox) {
                    String selectedHotelName = (String) hotelOptionsComboBox.getSelectedItem();
                    // You can handle the selected hotel name here if needed
                }
            }
        });
    }

    /**
     * Returns the currently selected hotel option from the combo box.
     *
     * @return the selected hotel name
     */
    public String getSelectedHotelOption() {
        return (String) hotelOptionsComboBox.getSelectedItem();
    }

    /**
     * Makes the frame visible.
     */
    public void showFrame() {
        frame.setVisible(true);
    }

    /**
     * Returns the main frame of the application.
     *
     * @return the JFrame instance
     */
    public JFrame getFrame() {
        return frame;
    }
}
