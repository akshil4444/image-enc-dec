import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import static java.nio.file.StandardCopyOption.*;

@SuppressWarnings("serial")
public class FrontEnd extends JFrame implements WindowListener,ActionListener{
	
	Font font;
	int flagb1=0,flagb2=0;
	String txtdir,imagedir;
	String txtfile,imagefile;
	String s,s1,s3;
	JTextField l1,l2,l3;
	JButton b1,b2,b3;
	JTextArea t11;
	JPanel mp,p1,p2,p3,p4,p5,p6;
	FrontEnd() throws Exception
	{
		//setExtendedState(MAXIMIZED_BOTH);
		
		
		/*font = Font.createFont(Font.TRUETYPE_FONT, new File("zipper.ttF"));
		font = font.deriveFont(Font.TRUETYPE_FONT, 32f);*/
		
		
		ImageIcon v = new ImageIcon("TXT.png");
		ImageIcon u = new ImageIcon("IMAGE.png");
		ImageIcon w = new ImageIcon("encrypt.png");
		b1=new JButton(v);
		b2=new JButton(u);
		b3=new JButton(w);
		b1.setPreferredSize(new Dimension(550,50));
	    b2.setPreferredSize(new Dimension(550,50));
	    b3.setPreferredSize(new Dimension(550,50));
		
		l1=new JTextField(21);
		l2=new JTextField(21);
		l3=new JTextField(21);
		
		
		
		l1.setEditable(false);
		l2.setEditable(false);
		l3.setEditable(false);
		
		l1.setText("CHOOSE TXT");
		l2.setText("CHOOSE IMAGE");
		l3.setText("ENCRYPT");
		
		l1.setFont(font);	
		l2.setFont(font);	
		l3.setFont(font);
		
		
		
		
		mp=new JPanel(new FlowLayout());
		p1=new JPanel();
		p2=new JPanel();
		p3=new JPanel();
		p4=new JPanel();
		p5=new JPanel();
		p6=new JPanel();
		
		
		p1.add(b1);
		p2.add(l1);
		p3.add(b2);
		p4.add(l2);
		p5.add(b3);
		p6.add(l3);
		mp.add(p1);
	    mp.add(p2);
	    mp.add(p3);
	    mp.add(p4);
	    mp.add(p5);
	    mp.add(p6);
		add(mp);
		
		ImageIcon img111 = new ImageIcon("logo.png");
		this.setIconImage(img111.getImage());
		
		setSize(600,450);
		setVisible(true);
		setTitle("ENCRYPT");
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
		
		
		addWindowListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
	}
	
		
	public static void main(String arg[])
	{
		try {
			new FrontEnd();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public void windowOpened(WindowEvent e) {	}
	public void windowClosing(WindowEvent e) { System.exit(0);}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if(e.getSource()==b1)
		{
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "TXT FILE", "txt");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		     
		            txtfile=chooser.getSelectedFile().getName();
		            s=chooser.getSelectedFile().getAbsolutePath();
		            l1.setText(txtfile);
		            flagb1=1;
		    }
		    
    		
		
		}//IF ENDS HERE OF B1
		
		
		if(e.getSource()==b2)
		{
			
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "JPG & PNG Images", "jpg", "png");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		     
		            imagefile=chooser.getSelectedFile().getName();
		            s1=chooser.getSelectedFile().getAbsolutePath();
		            l2.setText(imagefile);
		            flagb2=1;
		    
		    }
    		
    		
    		
		}//IF ENDS HERE OF B2
		
		
		if(e.getSource()==b3)
		{	
			if(flagb1==1 && flagb2==1)
			{
				
			FileDialog fd=new FileDialog(this,"Save as JPG File",FileDialog.SAVE);
            fd.setVisible(true);
            String dir=fd.getDirectory();
            String fname=fd.getFile();
            s3=dir+fname;
            
            if(dir!=null)
            {
            	l3.setText("WAIT..WE..ARE......ENCRYPTING................");
            	try {
				encrypt(s1);
            	} catch (Exception e1) {	e1.printStackTrace(); }
            	Path p1 = Paths.get(imagefile);
            	Path p2 = Paths.get(s3);
            	try {
				Files.copy( p1,p2, COPY_ATTRIBUTES);
				Files.delete(p1);
            	} catch (IOException e1) {e1.printStackTrace();}
			
            	l3.setText("SAVED");
            	}
            else
            {	l3.setText("SAVE WITH 'FILENAME.JPG' ");	}
			
			}
			else
			{l3.setText("LOAD IMAGE AND TXT PROPERLY");}
            
            
            
		}//IF ENDS HERE OF B3
		
		
		
		
		
		
	}//Action Performed Ends Here

////----------User Defined FUNCTION-------------------------------////

	public static String makeEightBit(String z)
	{
		int l=z.length();
		int x=8-l;
		if(l==8)
		return z;
		else
		{
			for(int i=0;i<x;i++)
			z="0".concat(z);
		}
		return z;
	}//make 8 bit ends here

	public void MakeBinFile(String s) throws Exception{
		File f=new File(s);
		FileReader fr = new FileReader(f);
		BufferedReader bf=new BufferedReader(fr);
		File fn = new File("Temp-Binary");
		FileOutputStream fos = new FileOutputStream(fn);
		DataOutputStream dos = new DataOutputStream(fos);
		String eightbit=new String();
		String fourbit1=new String();
		String fourbit2=new String();
		String bin = new String();
		int value=0;
		while((value = bf.read()) != -1)
		{
			bin=Integer.toBinaryString(value);
			eightbit=makeEightBit(bin);
			//System.out.println(eightbit);
			fourbit1=eightbit.substring(0,4);
			fourbit2=eightbit.substring(4,8);
			dos.writeBytes(fourbit1);
			dos.writeBytes("\r\n");
			dos.writeBytes(fourbit2);
			dos.writeBytes("\r\n");
		}
		dos.writeBytes("0000");dos.writeBytes("\r\n");dos.writeBytes("0000");
		fr.close();
		dos.close();
		fos.close();
	}//ENDS OF MAKE BIN FILE
	
	public void encrypt(String s1) throws Exception
	{	MakeBinFile(s);
		File img = new File(s1);
		BufferedImage bimg = ImageIO.read(img);
		BufferedImage dest = new BufferedImage(bimg.getWidth(), bimg.getHeight(), BufferedImage.TYPE_INT_ARGB);
		File file = new File("Temp-Binary");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line;
		
		
		int pixel[][] = new int[bimg.getWidth()][bimg.getHeight()];
		for(int i=0;i<bimg.getWidth();i++)
		for(int j=0;j<bimg.getHeight() ;j++)
		{ 
			pixel[i][j]=bimg.getRGB(i,j);
		}
		
		char[] c=new char[4];
		
		int temp;
		int[] pos=new int[4];
		for(int i=0;i<bimg.getWidth();i++)
		for(int j=0;j<bimg.getHeight();j++)
		{ temp=pixel[i][j];
		line = bufferedReader.readLine();
		if((line) != null)
		{c=line.toCharArray();}

		
	 	pos[0]=(temp>>24)&0x00000001; 
			pos[1]=(temp>>16)&0x00000001;
			pos[2]=(temp>>8)&0x00000001; 
			pos[3]=(temp)&0x00000001;
			int val = 0,bit = 0;
		 	for(int k=0;k<4;k++)
		 	{
		 		
		 		switch(k)
		 		{
		 		case 3:val=1;break;
		 		case 2:val=256;break;
		 		case 1:val=65536;break;
		 		case 0:val=16777216;break;
		 		default :System.out.println("Defalut in switch");break;
		 		}
		 	
		 		if(c[k]=='0'){bit=0;}
		 		if(c[k]=='1'){bit=1;}
		 		if(bit==0&&pos[k]==1)
		 		{temp=temp&(~val);}
		 		if(bit==1&&pos[k]==0)
		 		{temp=temp^val;}
		 	}
		 		
	    dest.setRGB(i,j,temp);
	    
		}
		fileReader.close();

	    ImageIO.write(dest, "png" ,new File(imagefile));

	}
	

	
//////////////////////////---------------EndOf Functions---------------------/////////////////////
	
}//Class Ends Here
