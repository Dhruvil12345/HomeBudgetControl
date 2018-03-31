/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import Project.Login;
import Project.SignUp;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Welcome_Page 
{
    Toolkit tk;
    public void welcome()
    { 
        tk = Toolkit.getDefaultToolkit();
        int x = (int)tk.getScreenSize().getWidth();
        int y = (int)tk.getScreenSize().getHeight();
        int mx = x/2;
        int wx = mx-200;
        JFrame main = new JFrame("WELCOME");
        main.setLayout(null);
        JLabel welcome = new JLabel("WELCOME");
        welcome.setFont(new Font("AERIAL BLACK",Font.BOLD,75));
        welcome.setBounds(wx, 0, 400, 100);
        welcome.setForeground(Color.white);
        JButton login = new JButton("Login");
        int lo = mx-250;
        login.setBounds(lo,300,500,40);// Left,Top,Width,Height
        login.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                 main.setVisible(false);
                 new Login(main);     
            }
        });
        
        JButton signup = new JButton("Signup");
        
        signup.setBounds(lo,350,500,40);// Left,Top,Width,Height
        signup.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent ae) {
                main.setVisible(false);
                 new SignUp(main);
                 
            }
            
        });
        main.getContentPane().setBackground(Color.cyan);
        main.add(login);
        main.add(signup);
        main.setBackground(Color.cyan);
        main.add(welcome);
        main.setVisible(true);
        main.setExtendedState(Frame.MAXIMIZED_BOTH);
        main.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public static void main(String[] r)
    {
        Welcome_Page n =  new Welcome_Page();
        n.welcome();
    }
}