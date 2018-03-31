package Project;
import static Project.Frame_4.fname;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
public class Search{
    private JFrame frame,FileName;
    private JTable table1;
    private JScrollPane scroll;
    private Toolkit kit;
    private TableRowSorter<TableModel>rowsorter;
    private JTextField search,fileText;
    private JLabel fileLabel;
    private JButton ok,print;
    private DefaultTableModel model1;
    static String fname;
    public Search(DefaultTableModel model) {
        kit = Toolkit.getDefaultToolkit();
        int x = (int)kit.getScreenSize().getWidth();
        int y = (int)kit.getScreenSize().getHeight();
        int midy = y/2;
        int midx = x/2;
        
        model1 = model;
        
        table1 = new JTable();
        table1.setModel(model1);
        scroll = new JScrollPane(table1);
        scroll.setBounds(0,midy,x,(int)(midy-0.15*midy));       
        rowsorter = new TableRowSorter<>(table1.getModel());
        search = new JTextField();
        table1.setRowSorter(rowsorter);
        search.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent de) {
                String text = search.getText();
                if(text.trim().length() == 0)
                {
                    rowsorter.setRowFilter(null);
                }
                else{
                    rowsorter.setRowFilter(RowFilter.regexFilter("(?i)"+ text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                String text = search.getText();
                if(text.trim().length()==0)
                {
                    rowsorter.setRowFilter(null);
                }
                else{
                    rowsorter.setRowFilter(RowFilter.regexFilter("(?i)"+text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                 throw new UnsupportedOperationException("Not supported yet.");
            }
            
        });
        frame = new JFrame("Search");
        frame.add(scroll);
        frame.setLayout(null);
        JLabel label = new JLabel("Search:");
        label.setBounds(midx-200, 180, 120, 25);
        search.setBounds(midx-150, 180, 300, 20);
        print = new JButton("Print");
        print.setBounds(midx-60, 230, 100, 40);
        print.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                getFileName();
            }
            
        });
        frame.add(search);
        frame.add(label);
        frame.add(print);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(Color.CYAN);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);       
    }
    public void getFileName(){
        FileName = new JFrame("FileName");
        FileName.setLayout(null);
        FileName.setSize(500,150);
        FileName.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        FileName.setLocationRelativeTo(null);
        fileLabel = new JLabel("FileName:");
        fileLabel.setBounds(5,5,90,15);
        fileText = new JTextField();
        fileText.setBounds(80,5,400,25);       
        ok = new JButton("OK");
        ok.setBounds(378,45,100,40);
        ok.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                fname=fileText.getText();
                FileName.setVisible(false);
                 frame.setVisible(true);
                 getPrintPDF();
            }
            
        });
        
        FileName.add(fileLabel);
        FileName.add(fileText);
        FileName.add(ok);
        FileName.setResizable(false);
        FileName.setVisible(true);
        Search.fname=fileText.getText();
    }
    public void getPrintPDF()
    {
        Document doc = new Document();
        try{
            PdfWriter writer;
            int count = table1.getRowCount();
            writer = PdfWriter.getInstance(doc, new FileOutputStream(fname+".pdf"));
            doc.open();
            PdfPTable tab=new PdfPTable(8);
            tab.addCell("Id");
            tab.addCell("Item Name");
            tab.addCell("Item's Rate");
            tab.addCell("Item Quantity");
            tab.addCell("Item's Unit");
            tab.addCell("Purchase Date");
            tab.addCell("Total Cost");
            tab.addCell("Item Type");
            
            int[] s = new int[count];
            for(int j=0;j<count;j++){
                s[j] = table1.convertRowIndexToModel(j);        
            }
            
            for(int i=0;i<count;i++){
            Object obj1 = GetData(table1, s[i], 0);
            Object obj2 = GetData(table1, s[i], 1);
            Object obj3 = GetData(table1, s[i], 2);
            Object obj4 = GetData(table1, s[i], 3);
            Object obj5 = GetData(table1, s[i], 4);
            Object obj6 = GetData(table1, s[i], 5);
            Object obj7 = GetData(table1, s[i], 6);
            Object obj8 = GetData(table1, s[i], 7);
            String value1=obj1.toString();
            String value2=obj2.toString();
            String value3=obj3.toString();
            String value4=obj4.toString();
            String value5=obj5.toString();
            String value6=obj6.toString();
            String value7=obj7.toString();
            String value8=obj8.toString();
            tab.addCell(value1);
            tab.addCell(value2);
            tab.addCell(value3);
            tab.addCell(value4);
            tab.addCell(value5);
            tab.addCell(value6);
            tab.addCell(value7);
            tab.addCell(value8);
        }
          doc.add(tab);   
        } catch(Exception e)
        {
             System.out.println(e.getMessage());
        }
        doc.close();
    }
    public Object GetData(JTable table, int row_index, int col_index){
    return table.getModel().getValueAt(row_index, col_index);
}
}