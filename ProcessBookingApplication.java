import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.*;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.*;
import javax.swing.*;

/**
 * Created by Eugene Yamenko (s0247060) on 18/04/2014.
 */
public class ProcessBookingApplication extends JFrame {
    private List<Booking> bookings = new ArrayList<>(); // initialize bookings array list;
    private JTextArea textArea;
    private JMenuItem bubbleSortItem;
    private JMenuItem mergeSortItem;
    private JMenuItem linearSearchItem;
    private JMenuItem binarySearchItem;

    public ProcessBookingApplication() {
        setTitle("Travel Booking Processing Application"); // set title
        setSize(725, 500); // set window size
        setJMenuBar(getBookingMenuBar()); // set default menu bar
        add(getDisplayArea()); // add display area
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // set default close action
        setLocationByPlatform(true); // set start location
        setVisible(true); // make it visible
    }

    public static void main(String[] args) {
        new ProcessBookingApplication(); // fire up booking application
    }

    /**
     * Sort the given list of bookings by name using Bubble Sort algorithm.
     *
     * @param bookings a list of bookings
     * @return a list of sorted bookings
     */
    public static List<Booking> bubbleSort(List<Booking> bookings) {
        List<Booking> result = new ArrayList<>(bookings);
        int n = result.size(); // list's size

        do {
            int newN = 0;

            for (int idx = 1; idx < n; idx++) {
                // get bookings
                Booking booking1 = result.get(idx - 1);
                Booking booking2 = result.get(idx);

                if (booking1.getFullName().compareTo(booking2.getFullName()) > 0) { // if booking1's name is greater
                    // swap bookings
                    result.set(idx - 1, booking2);
                    result.set(idx, booking1);
                    newN = idx;
                }
            }

            n = newN;
        } while (n != 0);

        return result;
    }

    /**
     * Sort the given list of bookings by name using Merge Sort algorithm.
     *
     * @param bookings a list of bookings
     * @return a list of sorted bookings
     */
    public static List<Booking> mergeSort(List<Booking> bookings) {
        int size = bookings.size(); // capture the size

        if (size < 2) { // list of one or zero elements is sorted list
            return bookings;
        }

        int idxMid = size / 2; // get mid point
        List<Booking> left = mergeSort(new ArrayList<>(bookings.subList(0, idxMid))); // recursively sort left half
        List<Booking> right = mergeSort(new ArrayList<>(bookings.subList(idxMid, size))); // recursively sort right half

        return merge(left, right); // merge and return the list
    }

    private static List<Booking> merge(List<Booking> left, List<Booking> right) {
        List<Booking> result = new ArrayList<>();

        while (left.size() > 0 || right.size() > 0) { // while we have elements
            if (left.size() > 0 && right.size() > 0) { // if both half has elements
                if (left.get(0).getFullName().compareTo(right.get(0).getFullName()) < 0) { // if left name is less
                    result.add(left.get(0)); // add left's first element
                    left.remove(0);
                } else {
                    result.add(right.get(0)); // add right's first element
                    right.remove(0);
                }
            } else if (left.size() > 0) { // if only left half has elements
                result.add(left.get(0));
                left.remove(0);
            } else { // if only right half has elements
                result.add(right.get(0));
                right.remove(0);
            }
        }

        return result;
    }

    /**
     * Search the given list of bookings with a given full name using Linear
     * Search algorithm.
     *
     * @param bookings a list of bookings
     * @param fullName a full name to be searched
     * @return an index of booking (-1 if not found)
     */
    public static int linearSearch(List<Booking> bookings, String fullName) {
        for (int idx = 0, size = bookings.size(); idx < size; idx++) {
            if (bookings.get(idx).getFullName().compareTo(fullName) == 0) { // if equal
                return idx;
            }
        }

        return -1; // not found
    }

    /**
     * Search the given sorted list of bookings with a given full name using
     * Binary Search algorithm.
     *
     * @param bookings a list of bookings
     * @param fullName a full name to be searched
     * @return an index of booking (-1 if not found)
     */
    public static int binarySearch(List<Booking> bookings, String fullName) {
        int idxMin = 0, idxMax = bookings.size() - 1; // starting indexes

        while (idxMax >= idxMin) {
            int idxMid = (idxMin + idxMax) / 2; // get mid point
            int result = bookings.get(idxMid).getFullName().compareTo(fullName); // compare and get result

            if (result == 0) { // if equal
                return idxMid;
            } else if (result < 0) { // if less than fullName
                idxMin = idxMid + 1;
            } else {
                idxMax = idxMid - 1;
            }
        }

        return -1; // not found
    }

    /**
     * Creates display area as JPanel and adds text area to it.
     *
     * @return JPanel with JTextArea added to it
     */
    private JPanel getDisplayArea() {
        JPanel displayArea = new JPanel();
        displayArea.setLayout(new BorderLayout()); // set layout for display area
        displayArea.setBorder(BorderFactory.createTitledBorder("Display Area")); // title of the border
        textArea = new JTextArea();
        textArea.setEditable(false); // make text area non-editable
        textArea.setFont(new Font("Courier New", Font.PLAIN, 12)); // set fixed width font
        displayArea.add(new JScrollPane(textArea), BorderLayout.CENTER); // add text area to display area

        return displayArea;
    }

    /**
     * Creates menu bar and adds menus with items to it.
     *
     * @return JMenuBar with menus and menu items
     */
    private JMenuBar getBookingMenuBar() {
        // common action listeners
        ShowDataAndSortItemsListener showDataAndSortItemsListener = new ShowDataAndSortItemsListener();
        SearchItemsListener searchItemsListener = new SearchItemsListener();

        // create file menu items
        final JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(new OpenItemListener());
        final JMenuItem showDataItem = new JMenuItem("Show Data");
        showDataItem.addActionListener(showDataAndSortItemsListener);
        final JMenuItem saveDataItem = new JMenuItem("Save Data");
        saveDataItem.addActionListener(new SaveDataItemListener());
        final JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() { // ActionListener for exitItem
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // exit with the correct code
            }
        });

        // create sort data menu items
        bubbleSortItem = new JMenuItem("Bubble Sort");
        bubbleSortItem.addActionListener(showDataAndSortItemsListener);
        mergeSortItem = new JMenuItem("Merge Sort");
        mergeSortItem.addActionListener(showDataAndSortItemsListener);

        // create search data menu items
        linearSearchItem = new JMenuItem("Linear Search");
        linearSearchItem.addActionListener(searchItemsListener);
        binarySearchItem = new JMenuItem("Binary Search");
        binarySearchItem.addActionListener(searchItemsListener);

        // create file menu and add items to it
        final JMenu fileMenu = new JMenu("File") {
            {
                add(openItem);
                add(showDataItem);
                add(saveDataItem);
                add(exitItem);
            }
        };

        // create sort data menu and add items to it
        final JMenu sortDataMenu = new JMenu("Sort Data") {
            {
                add(bubbleSortItem);
                add(mergeSortItem);
            }
        };

        // create search data menu and add items to it
        final JMenu searchDataMenu = new JMenu("Search Data") {
            {
                add(linearSearchItem);
                add(binarySearchItem);
            }
        };

        // add menus to menu bar and return it
        return new JMenuBar() {
            {
                add(fileMenu);
                add(sortDataMenu);
                add(searchDataMenu);
            }
        };
    }

    /**
     * ActionListener for openItem.
     */
    private class OpenItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try (Scanner scanner = new Scanner(Paths.get("inBooking.txt"))) { // try-with-resources
                bookings.clear(); // clear the list

                while (scanner.hasNext()) { // iterate through all lines of file
                    try {
                        String[] items = scanner.nextLine().split(", "); // split the line
                        bookings.add(new Booking(Integer.parseInt(items[0]), items[1], Double.parseDouble(items[2]),
                                Double.parseDouble(items[3]), Double.parseDouble(items[4]),
                                Double.parseDouble(items[5]))); // add Booking object to bookings list
                    } catch (Exception ex) { // catch all possible exceptions
                        JOptionPane.showMessageDialog(textArea, ex); // show description of the exception
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(textArea, ex); // show description of the exception
            }
        }
    }

    /**
     * ActionListener for saveDataItem
     */
    private class SaveDataItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try (PrintWriter writer = new PrintWriter("outBooking.txt")) { // try-with-resources
                for (Booking booking : bookings) { // append each booking to the file
                    writer.println(String.format("%d, %s, %s, %s, %s, %s", booking.getID(), booking.getFullName(),
                            booking.getFlightCost(), booking.getAccommodationCost(), booking.getMealCost(),
                            booking.getTotalCost()));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(textArea, ex); // show description of the exception
            }
        }
    }

    /**
     * ActionListener for linearSearchItem and binarySearchItem.
     */
    private class SearchItemsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fullName = JOptionPane.showInputDialog(textArea, "Enter a name for searching:");
            int result = 0;

            if (Booking.isNameCorrect(fullName)) { // if entered name is correct
                if (e.getSource() == linearSearchItem) { // if linearSearchItem pressed
                    result = linearSearch(bookings, fullName);
                } else if (e.getSource() == binarySearchItem) { // if binarySearchItem pressed
                    result = binarySearch(bookings, fullName);
                }

                textArea.append(fullName + " is" + (result == -1 ? " not " : " ") + "found\n");
            } else { // if entered name is incorrect
                JOptionPane.showMessageDialog(textArea,
                        "No name entered or entered name is longer than 15 characters.");
            }
        }
    }

    /**
     * ActionListener for showDataItem, bubbleSortItem and mergeSortItem.
     */
    private class ShowDataAndSortItemsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.setText(null); // clear text area
            textArea.append(getFormattedLine(true, "Booking Id", "Booking Name", "Flight", "Accommodation", "Meal",
                    "Total cost")); // append header with separator

            if (e.getSource() == bubbleSortItem) { // if bubbleSortItem pressed
                bookings = bubbleSort(bookings);
            } else if (e.getSource() == mergeSortItem) { // if mergeSortItem pressed
                bookings = mergeSort(bookings);
            }

            // append bookings with separator
            if (!bookings.isEmpty()) { // if list is not empty
                for (Booking booking : bookings) {
                    textArea.append(getFormattedLine(false, booking.getID(), booking.getFullName(),
                            booking.getFlightCost(), booking.getAccommodationCost(), booking.getMealCost(),
                            booking.getTotalCost())); // append formatted line with booking's data
                }

                textArea.append(getFormattedLine(true)); // append separator
            }

            textArea.append(getFormattedLine(false, "Total bookings:", bookings.size())); // append footer
        }

        /**
         * Creates formatted line for text area.
         *
         * @param withSeparator with separator?
         * @param items an array of items
         * @return a formatted line as a String
         */
        private String getFormattedLine(boolean withSeparator, Object... items) {
            StringBuilder formattedLine = new StringBuilder();

            if (items.length > 0) { // if array is not empty
                for (Object item : items) {
                    formattedLine.append(String.format("%-16s", item)); // append each item
                }

                formattedLine.append('\n'); // new line at the end
            }

            if (withSeparator) { // if with separator
                for (int i = 0; i < 90; i++) { // append '-' char 90 times
                    formattedLine.append('-');
                }

                formattedLine.append('\n'); // new line at the end
            }

            return formattedLine.toString();
        }
    }
}