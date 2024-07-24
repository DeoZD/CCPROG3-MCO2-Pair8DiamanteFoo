import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HotelManagementView {

    private JFrame frame;
    private JTextArea displayArea;
    private JPanel panel;

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

    public void showFrame() {
        frame.setVisible(true);
    }
}
