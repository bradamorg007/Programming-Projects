package roomBookings;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * This class generates a table view of the final users day they would like to view.
 */
public class GenerateTableView extends JPanel {


    public static void runTable(CalenderToTableBridge bridgeData) {

        final Object rowData[][] = bridgeData.getCellData();
        final String columnNames[] = bridgeData.getColumnTime();

        final JTable table = new JTable(rowData, columnNames){

            public boolean isCellEditable(int data, int columns){

                return false;
            }

            public Component prepareRenderer(TableCellRenderer r, int data, int columns){

                Component component = super.prepareRenderer(r, data, columns);

                if(data % 2 == 0){
                    component.setBackground(Color.LIGHT_GRAY);
                }else {
                    component.setBackground(Color.GRAY);
                }

                return component;
            }
        };

        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame(bridgeData.getDataName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(scrollPane, BorderLayout.CENTER);
        table.setFillsViewportHeight(true);


        TableColumn column = null;
        for (int i = 0; i < bridgeData.getColumnTime().length; i++) {
            column = table.getColumnModel().getColumn(i);
            if (i > 0) {
                column.setPreferredWidth(200); //sport column is bigger
            } else {
                column.setPreferredWidth(100);
            }
        }

        frame.setSize(1000, 300);
        frame.setVisible(true);

    }

}
