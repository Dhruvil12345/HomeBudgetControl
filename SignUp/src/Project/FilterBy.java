package Project;

import static Project.Frame_4.fname;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

public class FilterBy {
    private JFrame frame,nameIt;
    private JDateChooser date1,date2,date3,date4;
    private JLabel label1,label2,from,to,FileName;
    private Toolkit tk;
    private int x,y,midx,midy;
    private DefaultTableModel model;
    private JScrollPane pane;
    private JTable table;
    private JComboBox jc;
    private JButton go1,print1,go2,print2,go3,print3,ok,graph1,graph2,graph3;
    private Connection conn;
    private Statement st;
    private String sql;
    private JTextField filename;
    public FilterBy(String id)
    {
        frame = new JFrame("Filter By");
        frame.setLayout(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(Color.cyan);
        tk = Toolkit.getDefaultToolkit();
        x = (int)tk.getScreenSize().getWidth();
        y = (int)tk.getScreenSize().getHeight();
        midx = x/2;
        midy = y/2;
        model = new DefaultTableModel();
        String column[] = {"Id","Item Name","Item's Rate","Item Quantity","Item's unit","Purchase Date","Total Cost","Item Type"};
        model.setColumnIdentifiers(column);
        table = new JTable(model);
        pane = new JScrollPane(table);
        pane.setBounds(midx, 0, midx,y-(int)(0.08*y));
        label1 = new JLabel("Select from the below choices :");
        label1.setBounds(100,50,200,40);
        String value[] = {"","before date","after date","between dates"};
        jc = new JComboBox(value);
        jc.setBounds(100,90,280,30);
        label2 = new JLabel("Select the date : ");
        label2.setBounds(100,120,200,40);
        date1 = new JDateChooser();
        date1.setDateFormatString("yyyy-MM-dd");
        date1.setBounds(100,160,280,30);
        
        date2 = new JDateChooser();
        date2.setDateFormatString("yyyy-MM-dd");
        date2.setBounds(100,160,280,30);
        from = new JLabel("From : ");
        from.setBounds(100,160,200,40);
        date3 = new JDateChooser();
        date3.setDateFormatString("yyyy-MM-dd");
        date3.setBounds(100,200,280,30);
        to = new JLabel("To : ");
        to.setBounds(100,230,200,40);
        date4 = new JDateChooser();
        date4.setDateFormatString("yyyy-MM-dd");
        date4.setBounds(100,270,280,30);
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn=DriverManager.getConnection("jdbc:sqlite:data.db");
            st=conn.createStatement();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        graph1 = new JButton("Graph1");
        graph1.setBounds(185,275,100,35);
        graph2 = new JButton("Graph2");
        graph2.setBounds(185,275,100,35);
        graph3 = new JButton("Graph3");
        graph3.setBounds(185,375,100,35);
        graph1.setVisible(false);
        graph2.setVisible(false);
        graph3.setVisible(false);
        
        go3 = new JButton("Go");
        go3.setBounds(100,320,100,35);
        go3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                sql="SELECT * from "+id+" where date(dop) between date('"+new SimpleDateFormat("yyyy-MM-dd").format(date3.getDate())+"') and date('"+new SimpleDateFormat("yyyy-MM-dd").format(date4.getDate())+"') order by date(dop);";
                try
                {
                    ResultSet rs=st.executeQuery(sql);
                    
                    model.getDataVector().removeAllElements();
                    model.fireTableDataChanged();
                    long id1=0;
                    while(rs.next())
                    {
                        System.out.println("1");
                        String In = rs.getString("iname");
                        String Ir = rs.getString("rate");
                        String Iq = rs.getString("quantity");
                        String Iu = rs.getString("unit");
                        String Pd = rs.getString("dop");
                        String Tc = rs.getString("total");
                        String It = rs.getString("type");                       
                        model.addRow(new Object[]{Long.toString(++id1),In,Ir,Iq,Iu,Pd,Tc,It});
                    }
                }
                catch(SQLException e)
                {
                    System.out.println(e.getMessage());
                }
                if(table.getModel().getRowCount()==0)
                {
                    graph3.setVisible(false);
                }
                else{
                    graph3.setVisible(true);
                    
                }
            }
        });
        go1 = new JButton("Go");
        go1.setBounds(100,210,100,35);
        go1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                String d1=new SimpleDateFormat("yyyy-MM-dd").format(date1.getDate());
                try
                {
                    sql="SELECT rowid,* from "+id+" where date(dop) < date('"+d1+"') order by date(dop);";
                    ResultSet rs=st.executeQuery(sql);
                    model.getDataVector().removeAllElements();
                    model.fireTableDataChanged();
                    long id1=0;
                    while(rs.next())
                    {
                        String In = rs.getString("iname");
                        String Ir = rs.getString("rate");
                        String Iq = rs.getString("quantity");
                        String Iu = rs.getString("unit");
                        String Pd = rs.getString("dop");
                        String Tc = rs.getString("total");
                        String It = rs.getString("type");
                        model.addRow(new Object[]{Long.toString(++id1),In,Ir,Iq,Iu,Pd,Tc,It});
                    }
                    
                }
                catch(SQLException e)
                {
                    System.out.println(e.getMessage());
                }
                if(table.getModel().getRowCount()==0)
                {
                    graph1.setVisible(false);
                }
                else{
                    graph1.setVisible(true);
                }
            }    
        });
        go2 = new JButton("Go");
        go2.setBounds(100,210,100,35);
        go2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                String d1=new SimpleDateFormat("yyyy-MM-dd").format(date2.getDate());
                try{
                sql="SELECT rowid,* from "+id+" where date(dop) > date('"+d1+"') order by date(dop);";
                ResultSet rs=st.executeQuery(sql);
                    model.getDataVector().removeAllElements();
                    model.fireTableDataChanged();
                    long id1=0;
                    while(rs.next())
                    {
                        String In = rs.getString("iname");
                        String Ir = rs.getString("rate");
                        String Iq = rs.getString("quantity");
                        String Iu = rs.getString("unit");
                        String Pd = rs.getString("dop");
                        String Tc = rs.getString("total");
                        String It = rs.getString("type");
                        model.addRow(new Object[]{Long.toString(++id1),In,Ir,Iq,Iu,Pd,Tc,It});
                    }
                }
                catch(SQLException e)
                {
                    System.out.println(e.getMessage());
                }
                if(table.getModel().getRowCount()==0)
                {
                    graph2.setVisible(false);
                }
                else{
                    graph2.setVisible(true);
                }
            }  
        });
        print1 = new JButton("Print");
        print1.setBounds(280,210,100,35);
        print2 = new JButton("Print");
        print2.setBounds(280,210,100,35);
        print3 = new JButton("Print");
        print3.setBounds(280,320,100,35);
        label2.setVisible(false);
        date1.setVisible(false);
        go1.setVisible(false);
        print1.setVisible(false); 
        date2.setVisible(false);
        go2.setVisible(false);
        print2.setVisible(false); 
        
        from.setVisible(false);
        to.setVisible(false);
        date3.setVisible(false);
        date4.setVisible(false);
        go3.setVisible(false);
        print3.setVisible(false);
        print1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                FilterBy.this.setFileName();
            }
            
        });
        print2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                FilterBy.this.setFileName();
            }
            
        });
        print3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                FilterBy.this.setFileName();
            }
            
        });
        jc.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                JComboBox jc1 = (JComboBox)ae.getSource();
                Object select = jc1.getSelectedItem();
                if(select.toString().equals("before date")){
                    label2.setVisible(false);
                    date1.setVisible(true);
                    go1.setVisible(true);
                    print1.setVisible(true);
                    graph2.setVisible(false);
                    graph3.setVisible(false);
                }
                else{
                    label2.setVisible(false);
                    date1.setVisible(false);
                    go1.setVisible(false);
                    print1.setVisible(false);
                }
                if(select.toString().equals("after date")){
                    label2.setVisible(false);
                    date2.setVisible(true);
                    go2.setVisible(true);
                    print2.setVisible(true); 
                    graph1.setVisible(false);
                    graph3.setVisible(false);
                }
                else{
                    label2.setVisible(false);
                    date2.setVisible(false);
                    go2.setVisible(false);
                    print2.setVisible(false); 
                }
                if(select.toString().equals("between dates"))
                {
                    label2.setVisible(false);
                    from.setVisible(true);
                    to.setVisible(true);
                    date3.setVisible(true);
                    date4.setVisible(true);
                    go3.setVisible(true);
                    print3.setVisible(true);
                    graph1.setVisible(false);
                    graph2.setVisible(false);
                }
                else{
                    label2.setVisible(false);
                    from.setVisible(false);
                    to.setVisible(false);
                    date3.setVisible(false);
                    date4.setVisible(false);
                    go3.setVisible(false);
                    print3.setVisible(false);
                }
            }
            
        });     
        frame.add(pane);
        frame.add(label1);
        frame.add(jc);
        frame.add(label2);
        frame.add(date1);
        frame.add(print1);
        frame.add(go1);
        frame.add(date2);
        frame.add(go2);
        frame.add(print2);
        frame.add(from);
        frame.add(date3);
        frame.add(to);
        frame.add(date4);
        frame.add(go3);
        frame.add(print3);
        frame.add(graph1);
        frame.add(graph2);
        frame.add(graph3);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        graph1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                analysis.GraphForFilterBy.setOp(jc.getSelectedItem().toString(), new SimpleDateFormat("yyyy-MM-dd").format(date1.getDate()), "", "", id);
                analysis.GraphForFilterBy.prepareGraph();
            }
        });
        graph2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                analysis.GraphForFilterBy.setOp(jc.getSelectedItem().toString(),new SimpleDateFormat("yyyy-MM-dd").format(date2.getDate()), "", "", id);
                analysis.GraphForFilterBy.prepareGraph();
            }
        });
        graph3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                analysis.GraphForFilterBy.setOp(jc.getSelectedItem().toString(), "", new SimpleDateFormat("yyyy-MM-dd").format(date3.getDate()), new SimpleDateFormat("yyyy-MM-dd").format(date4.getDate()), id);
                analysis.GraphForFilterBy.prepareGraph();
            }
        });
    }
    public void setFileName()
    {
        nameIt = new JFrame("FileName");
        nameIt.setLayout(null);
        nameIt.setSize(500,150);
        nameIt.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        nameIt.setLocationRelativeTo(null);
        FileName = new JLabel("FileName:");
        FileName.setBounds(5,5,90,15);
        filename = new JTextField();
        filename.setBounds(80,5,400,25);       
        ok = new JButton("OK");
        ok.setBounds(378,45,100,40);
        
        ok.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                fname=filename.getText();
                nameIt.setVisible(false);
                 frame.setVisible(true);
                 getPrintPDF();
            }
        });
        
        nameIt.add(FileName);
        nameIt.add(filename);
        nameIt.add(ok);
        nameIt.setResizable(false);
        nameIt.setVisible(true);
        Frame_4.fname=filename.getText();
    }
    public void getPrintPDF()
    {
        Document doc = new Document();
        try{
            PdfWriter writer;
            int count = table.getRowCount();
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
            for(int i=0;i<count;i++){
            Object obj1 = GetData(table, i, 0);
            Object obj2 = GetData(table, i, 1);
            Object obj3 = GetData(table, i, 2);
            Object obj4 = GetData(table, i, 3);
            Object obj5 = GetData(table, i, 4);
            Object obj6 = GetData(table, i, 5);
            Object obj7 = GetData(table, i, 6);
            Object obj8 = GetData(table, i, 7);
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
