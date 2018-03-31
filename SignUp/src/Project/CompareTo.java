package Project;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class CompareTo {
    private JFrame compareto;
    private JTextField newprice;
    private JComboBox type,searchby;
    private JButton ok;
    private JDateChooser specificDate,specificMonth,specificYear;
    private JLabel newPrice,newPrice2,newPrice3,type1,searchby1,searchDate1,diff,pp1,diff2,diff3,pp2,pp3;
    private String id2;
    private Long ids;
    private JTextField date,month,year,newprice1,newprice2,newprice3,difference,pp,pp12,pp13,difference2,difference3;
    private JButton fetch1,fetch2,fetch3;
    private JButton fetch4;
    private JTextField date_month,newprice4,difference4,previous4;
    private JLabel np4,d4,p4;
    private JDateChooser date_mon;
    private Connection con;
    Statement st;
    ResultSet rs;
    public CompareTo(Long id)
    {
        ids = id;
        id2 = id.toString();
        compareto = new JFrame("Compare To");
        compareto.setLayout(null);
        compareto.setSize(550, 400);
        compareto.setLocationRelativeTo(null);
        type1 = new JLabel("Choose Type:");
        type1.setBounds(5,10,100,35);
        String types[] ={"Petrol","Clothes","Vegetables","Fast Food","Daily Needed","Diesel","Electricity Bills"
        ,"Shopping","Household Items","Home Maintainence Expenses","Electrical Appliances","Mobile Accessories"
        ,"Electrical Accessories","Accessories Maintainence Expenses","School Expenses",
        "Books","College Expenses","Trip Expenses","Others"};
        type = new JComboBox(types);
        type.setBounds(110,10,400,30);
        searchby1 = new JLabel("Compare By:");
        searchby1.setBounds(5,60,140,35);
        String compareType[] = {"","Specific Date","Specific Month","Specific Month and Year","Specific Year"};
        searchby = new JComboBox(compareType);
        searchby.setBounds(110,60,400,30);
        
        specificDate = new JDateChooser();
        specificDate.setBounds(110,110,200,30);
        specificDate.setDate(new Date());
        specificDate.setDateFormatString("yyyy-MM-dd");
        date = new JTextField();
        date.setBounds(330,110,180,30);
        date.setVisible(false);
        
        specificMonth = new JDateChooser();
        specificMonth.setBounds(110,110,200,30);
        specificMonth.setDateFormatString("MM");
        specificMonth.setDate(new Date());
        DateFormat month1 = new SimpleDateFormat("MM");
        month = new JTextField();
        month.setBounds(330,110,180,30);
        
        specificYear = new JDateChooser();
        specificYear.setBounds(110,110,200,30);
        specificYear.setDateFormatString("yyyy");
        specificYear.setDate(new Date());
        DateFormat year1 = new SimpleDateFormat("yyyy");
        year = new JTextField();
        year.setBounds(330,110,180,30);
        
        date_mon = new JDateChooser();
        date_mon.setBounds(110,110,200,30);
        date_mon.setDateFormatString("yyyy-MM");
        date_mon.setDate(new Date());
        DateFormat dm = new SimpleDateFormat("yyyy-MM");
        date_month = new JTextField();
        date_month.setBounds(330,110,180,30);
        
        specificDate.addPropertyChangeListener(new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                 date.setText(new SimpleDateFormat("yyyy-MM-dd").format(specificDate.getDate()));
            }
        });
        specificDate.setVisible(false);
        specificMonth.addPropertyChangeListener(new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                month.setText(new SimpleDateFormat("MM").format(specificMonth.getDate()));
            }
            
        });
        specificYear.addPropertyChangeListener(new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                year.setText(new SimpleDateFormat("yyyy").format(specificYear.getDate()));
            }
            
        });
        date_mon.addPropertyChangeListener(new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                date_month.setText(new SimpleDateFormat("yyyy-MM").format(date_mon.getDate()));
            }
            
        });
        date_mon.setVisible(false);
        
        
        fetch1 = new JButton("OK");
        fetch1.setBounds(400,310,110,35);
        fetch1.setVisible(false);
        fetch2 = new JButton("OK");
        fetch2.setBounds(400,310,110,35);
        fetch2.setVisible(false);
        fetch3 = new JButton("OK");
        fetch3.setBounds(400,310,110,35);
        fetch3.setVisible(false);
        fetch4 = new JButton("OK");
        fetch4.setBounds(400,310,110,35);
        fetch4.setVisible(false);
        
        np4 = new JLabel("New Price");
        np4.setBounds(5,160,140,35);
        newprice4 = new JTextField();
        newprice4.setBounds(110,160,400,30);
        np4.setVisible(false);
        newprice4.setVisible(false);
        date_month.setVisible(false);
        date_mon.setVisible(false);
        
        newPrice3 = new JLabel("New Price");
        newPrice3.setBounds(5,160,140,35);
        newprice3 = new JTextField();
        newprice3.setBounds(110,160,400,30);
        newPrice3.setVisible(false);
        newprice3.setVisible(false);
        year.setVisible(false);
        specificYear.setVisible(false);
        
        month.setVisible(false);
        specificMonth.setVisible(false);
        newPrice2 = new JLabel("New Price");
        newPrice2.setBounds(5,160,140,35);
        newprice2 = new JTextField();
        newprice2.setBounds(110,160,400,30);
        newPrice2.setVisible(false);
        newprice2.setVisible(false);
        
        newPrice = new JLabel("New Price:");
        newPrice.setBounds(5,160,140,35);
        newprice1 = new JTextField();
        newprice1.setBounds(110,160,400,30);
        newPrice.setVisible(false);
        newprice1.setVisible(false);
        
        p4 = new JLabel("Previous Value:");
        p4.setBounds(5,210,100,35);
        previous4 = new JTextField();
        previous4.setBounds(110,210,400,30);
        previous4.setEditable(false);
        previous4.setVisible(false);
        p4.setVisible(false);
        
        pp1 = new JLabel("Previous Value:");
        pp1.setBounds(5,210,100,35);
        pp = new JTextField();
        pp.setBounds(110,210,400,30);
        pp.setEditable(false);
        pp.setVisible(false);
        pp1.setVisible(false);
        
        d4 = new JLabel("Difference:");
        d4.setBounds(5,260,100,35);
        difference4 = new JTextField();
        difference4.setBounds(110,260,400,30);
        difference4.setEditable(false);
        d4.setVisible(false);
        difference4.setVisible(false);
        
        diff = new JLabel("Difference:");
        diff.setBounds(5,260,100,35);
        difference = new JTextField();
        difference.setBounds(110,260,400,30);
        difference.setEditable(false);
        diff.setVisible(false);
        difference.setVisible(false);
        
        pp2 = new JLabel("Previous Value:");
        pp2.setBounds(5,210,100,35);
        pp12 = new JTextField();
        pp12.setBounds(110,210,400,30);
        pp12.setEditable(false);
        pp12.setVisible(false);
        pp2.setVisible(false);
        
        diff2 = new JLabel("Difference:");
        diff2.setBounds(5,260,100,35);
        difference2 = new JTextField();
        difference2.setBounds(110,260,400,30);
        difference2.setEditable(false);
        diff2.setVisible(false);
        difference2.setVisible(false);
        
        pp3 = new JLabel("Previous Value:");
        pp3.setBounds(5,210,100,35);
        pp13 = new JTextField();
        pp13.setBounds(110,210,400,30);
        pp13.setEditable(false);
        pp13.setVisible(false);
        pp3.setVisible(false);
        
        diff3 = new JLabel("Difference:");
        diff3.setBounds(5,260,100,35);
        difference3 = new JTextField();
        difference3.setBounds(110,260,400,30);
        difference3.setEditable(false);
        diff3.setVisible(false);
        difference3.setVisible(false);
        
        searchby.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                JComboBox searchby = (JComboBox)ae.getSource();
                Object selected = searchby.getSelectedItem();
                if(selected.toString().equals("Specific Date"))
                {
                    specificDate.setVisible(true);
                    newPrice.setVisible(true);
                    newprice1.setVisible(true);
                    pp3.setVisible(true);
                    pp13.setVisible(true);
                    diff3.setVisible(true);
                    difference3.setVisible(true);
                    fetch1.setVisible(true);
                   
                }
                else{
                    specificDate.setVisible(false);
                    newPrice.setVisible(false);
                    newprice1.setVisible(false);
                    pp3.setVisible(false);
                    pp13.setVisible(false);
                    diff3.setVisible(false);
                    difference3.setVisible(false);
                    fetch1.setVisible(false);
                }
                if(selected.toString().equals("Specific Month"))
                {
                    specificMonth.setVisible(true);
                    newPrice2.setVisible(true);
                    newprice2.setVisible(true);
                    pp2.setVisible(true);
                    pp12.setVisible(true);
                    diff2.setVisible(true);
                    difference2.setVisible(true);
                    fetch2.setVisible(true);
                }
                else{
                    specificMonth.setVisible(false);
                    newPrice2.setVisible(false);
                    newprice2.setVisible(false);
                    pp2.setVisible(false);
                    pp12.setVisible(false);
                    diff2.setVisible(false);
                    difference2.setVisible(false);
                    fetch2.setVisible(false);
                }
                if(selected.toString().equals("Specific Month and Year"))
                {
                    date_mon.setVisible(true);
                    np4.setVisible(true);
                    newprice4.setVisible(true);
                    p4.setVisible(true);
                    previous4.setVisible(true);
                    d4.setVisible(true);
                    difference4.setVisible(true);
                    fetch4.setVisible(true);
                }
                else{
                    date_mon.setVisible(false);
                    np4.setVisible(false);
                    newprice4.setVisible(false);
                    p4.setVisible(false);
                    previous4.setVisible(false);
                    d4.setVisible(false);
                    difference4.setVisible(false);
                    fetch4.setVisible(false);
                }
                if(selected.toString().equals("Specific Year"))
                {
                    specificYear.setVisible(true);
                    newPrice3.setVisible(true);
                    newprice3.setVisible(true);
                    pp1.setVisible(true);
                    pp.setVisible(true);
                    diff.setVisible(true);
                    difference.setVisible(true);
                    fetch3.setVisible(true);
                }
                else{
                    specificYear.setVisible(false);
                    newPrice3.setVisible(false);
                    newprice3.setVisible(false);
                    pp1.setVisible(false);
                    pp.setVisible(false);
                    diff.setVisible(false);
                    difference.setVisible(false);
                    fetch3.setVisible(false);
                }
            }
            
        });
        fetch1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                CompareTo.this.forDate(id2);
            }   
        });
        fetch2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                CompareTo.this.forMonth(id2);
            }
            
        });
        fetch3.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                CompareTo.this.forYear(id2);
            }
            
        });
        fetch4.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                CompareTo.this.forMonthAndYear(id2);
            }
            
        });
        compareto.add(type1);
        compareto.add(type);
        compareto.add(searchby);
        compareto.add(searchby1);
        compareto.add(specificDate);
        compareto.add(date);
        compareto.add(newPrice);
        compareto.add(newprice1);
        compareto.add(specificMonth);
        compareto.add(month);
        compareto.add(newPrice2);
        compareto.add(newprice2);
        compareto.add(year);
        compareto.add(specificYear);
        compareto.add(newPrice3);
        compareto.add(newprice3);
        compareto.add(pp);
        compareto.add(pp1);
        compareto.add(diff);
        compareto.add(difference);
        compareto.add(pp2);
        compareto.add(pp12);
        compareto.add(diff2);
        compareto.add(difference2);
        compareto.add(pp3);
        compareto.add(pp13);
        compareto.add(diff3);
        compareto.add(difference3);
        compareto.add(fetch1);
        compareto.add(fetch2);
        compareto.add(fetch3);
        compareto.add(np4);
        compareto.add(newprice4);
        compareto.add(p4);
        compareto.add(previous4);
        compareto.add(d4);
        compareto.add(difference4);
        compareto.add(date_mon);
        compareto.add(date_month);
        compareto.add(fetch4);
        
        compareto.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        compareto.setVisible(true);
        compareto.setResizable(false);
    }
    public void connectDatabase()
    {
        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:data.db");
            st = con.createStatement();
            
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void forDate(String id3){
        try{
        String is="";
        String typ2 = type.getSelectedItem().toString();
        String fetcher1 = newprice1.getText();
        double d = Double.parseDouble(fetcher1);
        this.connectDatabase();
        id3 = id2;
        String datefetch = date.getText();
        String sql = "SELECT type,rate from d"+id3+" WHERE type == '"+typ2+"' AND date(dop) == '"+datefetch+"';";
        try{
            rs = st.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        while(rs.next())
        {
            is = rs.getString("rate");
        }
        pp13.setText(is);
        double b = Double.parseDouble(is);
        double x = d-b;
        String sd = Double.toString(x);
        if(x<0){
            difference3.setText("-"+sd);
        }
        else{
            difference3.setText("+"+sd);
        }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void forMonth(String id4){
        try{
            String data="";
            String extra="";
            String typ2 = type.getSelectedItem().toString();
            String fetcher2 = newprice2.getText();
            double d = Double.parseDouble(fetcher2);
            this.connectDatabase();
            id4 = id2;
            String datafetch = month.getText();
            String sql;
            sql = "SELECT dop,type,rate from d"+id4+" WHERE type == '"+typ2+"' AND strftime('%m',dop) = '"+datafetch+"' ORDER BY dop DESC;";
            try{
            rs = st.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        while(rs.next())
        {
            data = rs.getString("rate");
            extra = rs.getString("dop");
        }
        pp12.setText(data+" "+"("+extra+")");
        double x = Double.parseDouble(data);
        double v = d-x;
        String b = Double.toString(v);
        if(v<0)
        {
            difference2.setText("-"+b);
        }
        else{
            difference2.setText("+"+b);
        }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void forYear(String id5)
    {
        try{
            String data="";
            String newData="";
            String typ2 = type.getSelectedItem().toString();
            String hi = newprice3.getText();
            double e = Double.parseDouble(hi);
            this.connectDatabase();
            id5 = id2;
            String datafetch = year.getText();
            String sql;
            sql = "SELECT dop,type,rate from d"+id5+" WHERE type like '"+typ2+"' AND strftime('%Y',date(dop)) = '"+datafetch+"';";
            try{
            rs = st.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        while(rs.next())
        {
            data = rs.getString("rate");
            newData = rs.getString("dop");
        }
        pp.setText(data+" "+"("+newData+")");
        double w = Double.parseDouble(data);
        double r = e-w;
        String hhi = Double.toString(r);
        if(r<0){
            difference.setText("-"+hhi);
        }
        else{
            difference.setText("+"+hhi);
        }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void forMonthAndYear(String id6){
        try{
        String is="";
        String he = "";
        String typ2 = type.getSelectedItem().toString();
        String fetcher1 = newprice4.getText();
        double d = Double.parseDouble(fetcher1);
        this.connectDatabase();
        id6 = id2;
        String datefetch = date_month.getText();
        String sql = "SELECT dop,type,rate from d"+id6+" WHERE type == '"+typ2+"' AND strftime('%Y-%m',date(dop)) = '"+datefetch+"' ORDER BY dop DESC;";
        try{
            rs = st.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if(rs.next())
        {
            is = rs.getString("rate");
            he = rs.getString("dop");  
        }
        previous4.setText(is+" "+"("+he+")");
        double b = Double.parseDouble(is);
        double x = d-b;
        String sd = Double.toString(x);
        if(x<0){
            difference4.setText("-"+sd);
        }
        else{
            difference4.setText("+"+sd);
        }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}