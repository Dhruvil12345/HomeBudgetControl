package Project;
import DataBaseManager.AddItem;
import Project.Login;
import Project.Profile_View;
import Project.SignUp;
import Project.Welcome_Page;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import Project.Search;
import Project.FilterBy;
import Project.CompareTo;
import Project.FilterBy;
public class Frame_4 {
    private JFrame frame4,log,nameIt;
    private JTextField userId,extra;
    private JButton profile,logout;
    private JTextField in,roi,qoi,dop,tcoi,i_id;
    private JComboBox combo,unit;
    private JButton modify,add,delete,print,compareto;
    private int srow=-1;
    private JDateChooser cal;
    private JButton ok,search,filterby,graph;
    private JLabel FileName;
    private JTextField filename;static String fname;
    private JTable table;
    Statement st1;
    Connection con;
    Toolkit tk;
    String iiid;ResultSet rs;
    private Long pid;
    public JFrame getFrame()
    {
        return frame4;     
    }
    public JButton button()
    {
        return profile;
    }
    public Frame_4(String id,JFrame log)
    {
        pid = Long.parseLong(id);
        tk = Toolkit.getDefaultToolkit();
        int x = (int)tk.getScreenSize().getWidth();
        int y = (int)tk.getScreenSize().getHeight();
        int midx = x/2;
        int midy = y/2;
        int wx =x-120;
        int ax= x-(120+10+80);
        int u = x-(120+10+80+140+10);
        frame4 = new JFrame("DashBoard");
        this.log=log;
        frame4.setLayout(null);
        JLabel in1 = new JLabel();
        JLabel unit1 = new JLabel();
        JLabel roi1 = new JLabel();
        JLabel qoi1 = new JLabel();
        JLabel dop1 = new JLabel();
        JLabel tcoi1 = new JLabel();
        modify = new JButton("Modify");
        add = new JButton("Add");
        delete = new JButton("Delete");
        print = new JButton("Print");
        search = new JButton("Search");
        filterby = new JButton("Filter By");
        compareto = new JButton("Compare To");
        graph = new JButton("Graph");
        modify.setBounds(5, 395, 150 ,45);
        add.setBounds(245, 395, 150, 45);
        delete.setBounds(5, 450, 150, 45);
        print.setBounds(245, 450, 150, 45);
        search.setBounds(5, 505, 150, 45);
        compareto.setBounds(5,560,150,45);
        graph.setBounds(245,560,150,45);
        filterby.setBounds(245, 505, 150, 45);
        frame4.add(add);
        frame4.add(modify);
        frame4.add(delete);
        frame4.add(print);
        frame4.add(search);
        frame4.add(filterby);
        frame4.add(compareto);
        frame4.add(graph);
        roi1 = new JLabel();
        roi1.setText("Item's Rate:");
        in1.setText("Item Name:");
        unit1.setText("Item's unit:");
        qoi1.setText("Item Quantity:");
        dop1.setText("Purchase Date:");
        tcoi1.setText("Total Cost:");
        in1.setBounds(5, 70, 80, 40);
        unit1.setBounds(5, 115, 140, 35);
        roi1.setBounds(5, 160, 140, 35);
        qoi1.setBounds(5, 205, 140, 35);
        dop1.setBounds(5, 250, 140, 35);
        tcoi1.setBounds(5, 295, 140, 35);
        unit = new JComboBox();
        String[] utype={"milligram","gram","litre","Pieces","kilogram","pound","millimeter square","centimeter square","meter square","kilometer square","mile","Volt","ohm"};
        unit = new JComboBox(utype);
        unit.setBounds(95, 115, 300, 30);
        unit.setToolTipText("Enter number of Items");
        qoi = new JTextField();
        qoi.setBounds(95, 205, 300, 30);
        qoi.setToolTipText("Enter Quantity Of Item");
        frame4.add(unit);
        frame4.add(qoi);
        frame4.add(qoi1);
        roi = new JTextField();
        roi.setBounds(95, 160, 300, 30);
        roi.setToolTipText("Enter Item's Price");
        tcoi = new JTextField();
        tcoi.setToolTipText("Total Cost Of Item");
        tcoi.setBounds(95, 295, 300, 30);
        tcoi.setEditable(false);
        userId = new JTextField();
        in = new JTextField();
        frame4.add(roi);
        frame4.add(roi1);
        frame4.add(tcoi1);
        frame4.add(tcoi);      
        String type[] ={"Petrol","Clothes","Vegetables","Fast Food","Daily Needed","Diesel","Electricity Bills"
        ,"Shopping","Household Items","Home Maintainence Expenses","Electrical Appliances","Mobile Accessories"
        ,"Electrical Accessories","Accessories Maintainence Expenses","School Expenses",
        "Books","College Expenses","Trip Expenses","Others"};
        //String data[] = {"1","1","1","1","1","1","s"};
        String column[] = {"Id","Item Name","Item's Rate","Item Quantity","Item's unit","Purchase Date","Total Cost","Item Type"};
        JScrollPane pane;
        DefaultTableModel model;
        table = new JTable();
        model = new DefaultTableModel();
        table.setModel(model);
        model.setColumnIdentifiers(column); 
        
        try{
            Class.forName("org.sqlite.JDBC");
            con=DriverManager.getConnection("jdbc:sqlite:data.db");
            String sql1="SELECT rowid,* FROM d"+id;
            //String sql1="SELECT * FROM d"+id;
            st1 = con.createStatement();
            
            try
            {
                rs = st1.executeQuery(sql1);
            }
            catch(SQLException e)
            {
                if(e.getMessage().equals("[SQLITE_ERROR] SQL error or missing database (no such table: d"+id+")"))
                {
                    st1.executeQuery("CREATE TABLE d"+id+"(iname TEXT,rate REAL,unit TEXT,quantity REAL,dop TEXT,total REAL,type TEXT);");
                    rs=st1.executeQuery(sql1);
                }
            }
            while(rs.next()){
                String In = rs.getString("iname");
                String Ir = rs.getString("rate");
                String Iq = rs.getString("quantity");
                String Iu = rs.getString("unit");
                String Pd = rs.getString("dop");
                String Tc = rs.getString("total");
                String It = rs.getString("type");
                iiid = rs.getString("rowid");
                //model.addRow(new Object[]{In,Ir,Iq,Iu,Pd,Tc,It});
                model.addRow(new Object[]{iiid,In,Ir,Iq,Iu,Pd,Tc,It});
                
            }
            table.setModel(model);
        } catch (SQLException ex1) {
           System.out.println(ex1.getMessage());
        }catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
       // model.addRow(data);
        pane = new JScrollPane(table);
        pane.setBounds(midx, 70, midx-(1*y/100), y-(20*y/100));
        frame4.add(pane);
        JLabel combo1 = new JLabel();
        combo1.setText("Type:");
        combo1.setBounds(5, 340, 140, 35);
        combo = new JComboBox(type);
        combo.setBounds(95, 340, 300, 30);
        frame4.add(combo1);
        frame4.add(combo);
        in.setBounds(95, 70, 300, 30);
        in.setToolTipText("Enter Item's Name");
        dop = new JTextField();
        cal = new JDateChooser();
        cal.setDateFormatString("yyyy-MM-dd");
        cal.setBounds(95, 250, 300, 30);
        dop.setToolTipText("Enter Date for Purchase Item");
        frame4.add(dop1);
        frame4.add(cal);
        userId.setBounds(u, 10, 140, 35);
        userId.setText(" User ID :  "+id);       
        userId.setEditable(false);
        frame4.add(in1);
        frame4.add(unit1);
        profile = new JButton("profile");
        profile.setBounds(ax,10,80,30);
        logout = new JButton("LOGOUT");
        logout.setBounds(wx,10,120,30);    
        frame4.add(logout);
        frame4.add(profile);
        frame4.add(in);
        frame4.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame4.getContentPane().setBackground(Color.cyan);
        frame4.add(userId);
        frame4.setDefaultCloseOperation(EXIT_ON_CLOSE);
        profile.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                try
                {
                    Profile_View pw=new Profile_View(id,frame4);
                    JFrame frame=pw.getFrame();
                    frame4.setVisible(false);
                    frame.setVisible(true);
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(frame4, e.getMessage());
                }
            }         
        });
        logout.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                frame4.setVisible(false);
                log.setVisible(true);
            }          
        });
        add.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                    
                    rs=st1.executeQuery("SELECT rowid from d"+id+";");
                    iiid=Integer.toString(1);
                    while(rs.next())
                    {
                        iiid=rs.getString("rowid");
                        System.out.println(iiid+" ");
                        iiid=Long.toString(Long.parseLong(iiid)+1);
                    }
                    tcoi.setText(""+Double.parseDouble(roi.getText())*Double.parseDouble(qoi.getText()));
                    new AddItem(frame4,Long.parseLong(id),in.getText(),Double.parseDouble(roi.getText()),unit.getSelectedItem().toString(),Double.parseDouble(qoi.getText()),cal.getDate(),combo.getSelectedItem().toString());
                    model.addRow(new Object[]{iiid,in.getText(),roi.getText(),qoi.getText(),unit.getSelectedItem().toString(),new SimpleDateFormat("yyyy-MM-dd").format(cal.getDate()),tcoi.getText(),combo.getSelectedItem().toString()});
                    //JOptionPane.showMessageDialog(null, "Successful");
                    
                }
                catch(Exception e)
                {
                    if(e.getMessage().equals("empty String"))
                    {
                        JOptionPane.showMessageDialog(null, "All field are required");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Enter valid credentials"+" "+e.getMessage());
                    }
                }
            }
            
        });
        table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent ae)
      {
          srow=table.getSelectedRow();
          in.setText(model.getValueAt(srow, 1).toString()); 
          roi.setText(model.getValueAt(srow, 2).toString());
          qoi.setText(model.getValueAt(srow, 3).toString());
          unit.setSelectedItem(model.getValueAt(srow, 4).toString());                   
          //dop.setText(model.getValueAt(srow, 5).toString());         
          try
          {
              cal.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(model.getValueAt(srow,5).toString()));
          }
          catch(ParseException e){}
          tcoi.setText(model.getValueAt(srow, 6).toString());
          combo.setSelectedItem(model.getValueAt(srow, 7).toString());
      } });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try
                {
                    if(srow==-1)
                    {
                        JOptionPane.showMessageDialog(null, "Please select a row first");
                    }
                    else
                    {
                        doVacuum();
                        st1.executeUpdate("DELETE FROM d"+id+" where rowid="+(srow+1)+";");
                        doVacuum();
                        model.removeRow(srow);
                        updateColNo(model,srow);
                        JOptionPane.showMessageDialog(null, "Delete Successfully");
                        srow=-1;
                    }
                }
                catch(SQLException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            private void updateColNo(DefaultTableModel model,int n) {
                for(int i=n;i<model.getRowCount();i++)
                {
                    model.setValueAt(new Integer(i+1), i, 0);
                }
            }
        });       
        print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                getFileName();
            }
        });    
    
    modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(srow==-1)
                {
                    JOptionPane.showMessageDialog(null, "Please select a row first");
                }
                else
                {
                    
                    try
                    {
                        double total=Double.parseDouble(roi.getText())*Double.parseDouble(qoi.getText());
                        tcoi.setText(Double.toString(total));
                        st1.executeUpdate("UPDATE D"+id+" SET iname='"+in.getText()+"',rate="+Double.parseDouble(roi.getText())+",unit='"+unit.getSelectedItem().toString()+"',quantity="+Double.parseDouble(qoi.getText())+",dop='"+new SimpleDateFormat("yyyy-MM-dd").format(cal.getDate())+"',total="+total+",type='"+combo.getSelectedItem().toString()+"' where rowid="+(srow+1)+";");
                        model.setValueAt(srow+1, srow, 0);
                        model.setValueAt(in.getText() ,srow, 1);
                        model.setValueAt(roi.getText(), srow, 2);        
                        model.setValueAt(qoi.getText(), srow, 3);
                        model.setValueAt(unit.getSelectedItem().toString(), srow, 4);
                        model.setValueAt(new SimpleDateFormat("yyyy-MM-dd").format(cal.getDate()), srow, 5);
                        model.setValueAt(tcoi.getText(), srow, 6);
                        model.setValueAt(combo.getSelectedItem().toString(), srow, 7);
                        JOptionPane.showMessageDialog(null, "Update Successful");
                        srow=-1;
                    }
                    catch(SQLException e)
                    {
                        System.out.println(e.getMessage());
                    }
                    catch(Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
    search.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                 new Search(model);
            }
        
    });
    filterby.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                //new FilterBy("d"+pid.toString()).setVisible(true);
                new FilterBy("d"+pid.toString());
            }
        
    });
    graph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                new analysis.GraphForMainFrame("d"+pid);
            }
        });
    compareto.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                new CompareTo(pid);
            }
        
    });
    }
    void doVacuum()
    {
        try
        {
            st1.executeQuery("VACUUM");
        }
        catch(SQLException e)
        {
            
        }
    }
    public void getFileName()
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
                 frame4.setVisible(true);
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
/*check = new JButton("Check");
      check.setBounds(90,100, 100, 50);
      frame4.add(check);
      check.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                frame4.setVisible(false);
                new AddItem(userId.getText());
                
            }  
      });*/