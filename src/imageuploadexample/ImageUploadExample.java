/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package imageuploadexample;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author nadya
 */
public class ImageUploadExample extends JFrame implements ActionListener{

    private JPanel panel;
    private JButton button;
    private JLabel img;
    private JLabel label;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new ImageUploadExample().setVisible(true);
    }
    
   ImageUploadExample() {    
      this.setTitle("Image upload in Swing Example");
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      createUI();
      this.setSize(800, 600);      
//      this.setLocationRelativeTo(null);  
      this.setVisible(true);
   }

   private void createUI(){  
      panel = new JPanel();
      LayoutManager layout = new FlowLayout();  
      panel.setLayout(layout);       
      button = new JButton("Upload photo to resources dir!");
      img = new JLabel();
      label = new JLabel();
      button.addActionListener(this);

      panel.add(button);
      panel.add(label);
      panel.add(img);
      this.getContentPane().add(panel, BorderLayout.CENTER);    
   }  
   
   @Override
    public void actionPerformed(ActionEvent e) {
       JFileChooser fileChooser = new JFileChooser();
       fileChooser.addChoosableFileFilter(new ImageFilter());
       fileChooser.setAcceptAllFileFilterUsed(false);

       int option = fileChooser.showOpenDialog(this);
       if(option == JFileChooser.APPROVE_OPTION){
          File file = fileChooser.getSelectedFile();
          label.setText("File Selected: " + file.getName());
          try {               
               Path resourceDirectory = Paths.get("src","resources");
               String absolutePath = resourceDirectory.toFile().getAbsolutePath();
              
               FileChannel src = new FileInputStream(file).getChannel();
               FileChannel dest = new FileOutputStream(new File(absolutePath+"/"+file.getName())).getChannel();
               dest.transferFrom(src, 0, src.size());
               src.close();
               dest.close();
               ImageIcon imgIcon = new ImageIcon(absolutePath+"/"+file.getName());
               img.setIcon(imgIcon);
               img.setSize(imgIcon.getIconWidth(), imgIcon.getIconHeight());
           } catch (Exception ex) {
               // TODO Auto-generated catch block
               ex.printStackTrace();
           }
       }else{
          label.setText("Open command canceled");
       }
    }
} 