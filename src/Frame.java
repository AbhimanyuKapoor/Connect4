import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;

public class Frame extends JFrame implements ActionListener
{
	ImageIcon logo;
	
	//1 is Yellow, -1 is Red
	final int ROUND_VAL=100;
	boolean yellowChance=true;
	boolean gameOver;
	
	JButton[][] buttons=new JButton[6][7];
	int[][] board=new int[6][7];
	
	JPanel btnPanel;
	JLabel textLabel;
	JButton nextButton;
	
	int continueCount=0;
	int oppoCount=0;
	int rowCurrent;
	int columnCurrent;
	
	public Frame()
	{
		logo=new ImageIcon("Connect4.png");
		this.setIconImage(logo.getImage());
		
		this.setLayout(new BorderLayout());
		
		textLabel=new JLabel();
		textLabel.setBackground(Color.BLACK);
		textLabel.setOpaque(true);
		textLabel.setFont(new Font("MV Boli", Font.BOLD, 25));
		textLabel.setForeground(Color.YELLOW);
		textLabel.setText("Yellow's Chance");
		textLabel.setHorizontalAlignment(JLabel.CENTER);
		
		btnPanel=new JPanel();
		btnPanel.setLayout(new GridLayout(6,7));	
		for(int i=0; i<buttons.length; i++)
		{
			for(int j=0; j<buttons[0].length; j++)
			{
				buttons[i][j]=new JButton();
				buttons[i][j].addActionListener(this);
				buttons[i][j].setFocusable(false);
				buttons[i][j].setPreferredSize(new Dimension(60, 60));
				buttons[i][j].setBorder((Border) new RoundBtn(ROUND_VAL, 0));
				btnPanel.add(buttons[i][j]);
			}
		}
		nextButton=new JButton();
		nextButton.addActionListener(this);
		nextButton.setFocusable(false);
		nextButton.setForeground(Color.BLUE);
		nextButton.setBackground(Color.BLACK);
		nextButton.setFont(new Font("Courier", Font.BOLD, 25));
		nextButton.setText("New Game");
		
		//Always add frame properties in the end, that way components are formed first, then frame is seen
		//So we don't need to revalidate the frame
		this.add(textLabel, BorderLayout.NORTH);
		this.add(btnPanel);
		this.add(nextButton, BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==nextButton)
		{
			this.dispose();
			new Frame();
		}
		if(!gameOver)
		{
			for(int i=0; i<buttons.length; i++)
				for(int j=0; j<buttons[0].length; j++)
					if(e.getSource()==buttons[i][j])
						dropCoin(j); //Drop Coin in that column
		}
	}
	
	public void checkWinner(int i, int j)
	{
		boolean tie=true;
		for(int r=0; r<board.length; r++)
		{
			for(int c=0; c<board[0].length; c++)
			{
				if(board[r][c]==0)
				{
					tie=false; 
					break;
				}
			}
			if(!tie)
				break;
		}
		if(tie)
		{
			gameOver=true;
			textLabel.setForeground(Color.BLUE);
			textLabel.setText("It is a Tie.");
		}
		
					
		int checkNumber;
		if(yellowChance)
			checkNumber=1;
		else 
			checkNumber=-1;
		
		//We need separate try and catch so that if try doesn't work it doesn't ignore all other directions of winning
		try {
			if(board[i][j+1]==checkNumber)
			{
				oppoCount=0;
				continueCount=0;
				continueCount++;
				continueDirection(i, j+1, 0, 1);
			}
		}
		catch (ArrayIndexOutOfBoundsException ignored) {
			// TODO: handle exception
		}
		
		try {
			if(board[i][j-1]==checkNumber)
			{
				oppoCount=0;
				continueCount=0;
				continueCount++;
				continueDirection(i, j-1, 0, -1);
			}
		}
		catch (ArrayIndexOutOfBoundsException ignored) {
			// TODO: handle exception
		}
		
		try {
			if(board[i-1][j-1]==checkNumber)
			{
				oppoCount=0;
				continueCount=0;
				continueCount++;
				continueDirection(i-1, j-1,-1, -1);
			}
		}
		catch (ArrayIndexOutOfBoundsException ignored) {
			// TODO: handle exception
		}
		
		try {
			if(board[i+1][j+1]==checkNumber)
			{
				oppoCount=0;
				continueCount=0;
				continueCount++;
				continueDirection(i+1, j+1, 1, 1);
			}
		}
		catch (ArrayIndexOutOfBoundsException ignored) {
			// TODO: handle exception
		}
		
		try {
			if(board[i+1][j]==checkNumber)
			{
				oppoCount=0;
				continueCount=0;
				continueCount++;
				continueDirection(i+1, j, 1, 0);
			}
		}
		catch (ArrayIndexOutOfBoundsException ignored) {
			// TODO: handle exception
		}
		
		try {
			if(board[i-1][j]==checkNumber)
			{
				oppoCount=0;
				continueCount=0;
				continueCount++;
				continueDirection(i-1, j, -1, 0);
			}
		}
		catch (ArrayIndexOutOfBoundsException ignored) {
			// TODO: handle exception
		}
		
		try {
			if(board[i-1][j+1]==checkNumber)
			{
				oppoCount=0;
				continueCount=0;
				continueCount++;
				continueDirection(i-1, j+1, -1, 1);
			}
		}	
		catch (ArrayIndexOutOfBoundsException ignored) {
			// TODO: handle exception
		}
		
		try {
			if(board[i+1][j-1]==checkNumber)
			{
				oppoCount=0;
				continueCount=0;
				continueCount++;
				continueDirection(i+1, j-1, 1, -1);
			}
		}
		catch (ArrayIndexOutOfBoundsException ignored) {
			// TODO: handle exception
		}
	}
	
	//Continue in one direction if we have an adjacent of same color.
	public void continueDirection(int i, int j, int iAdd, int jAdd)
	{
		int checkNumber;	
		//Single try catch for one direction checking so that if it goes out of bounds in that direction it goes to check the next winning direction
		try {
			if(continueCount<3)	
			{
				if(yellowChance)
					checkNumber=1;
				else 
					checkNumber=-1;
					
				if(board[i+iAdd][j+jAdd]==checkNumber)
				{
					continueCount++;
					continueDirection(i+iAdd, j+jAdd, iAdd, jAdd);
				}
				if(board[i+iAdd][j+jAdd]==(checkNumber)*-1 || board[i+iAdd][j+jAdd]==0)
				{
					if(oppoCount<1)
					{
						continueCount=0;
						oppoCount=1;
						continueDirection(i, j, iAdd*-1, jAdd*-1);
					}
				}
			}
			if(continueCount==3)
			{
				buttons[rowCurrent][columnCurrent].setBorder((Border) new RoundBtn(ROUND_VAL, 3));
				buttons[i][j].setBorder((Border) new RoundBtn(ROUND_VAL, 3));
				declareWinner();
			}
		}
		catch (ArrayIndexOutOfBoundsException ignored) {
			// TODO: handle exception
		}
	}
	
	public void declareWinner()
	{
		textLabel.setForeground(Color.GREEN);
		if(yellowChance)
			textLabel.setText("Yellow Wins!");
		else 
			textLabel.setText("Red Wins!");
		gameOver=true;
	}
	
	public void dropCoin(int column)
	{
		for(int i=buttons.length-1; i>=0; i--)
		{
			if(board[i][column]==0)
			{
				if(yellowChance)
				{
					buttons[i][column].setBorder(new RoundBtn(ROUND_VAL, 1));
					textLabel.setForeground(Color.RED);
					textLabel.setText("Red's Chance");
					board[i][column]=1;
					
					rowCurrent=i;
					columnCurrent=column;
					
					checkWinner(i, column);
					yellowChance=false;
				}
				else 
				{
					buttons[i][column].setBorder(new RoundBtn(ROUND_VAL, 2));
					textLabel.setForeground(Color.YELLOW);
					textLabel.setText("Yellow's Chance");
					board[i][column]=-1;
					
					rowCurrent=i;
					columnCurrent=column;
					
					checkWinner(i, column);
					yellowChance=true;
				}
				break;
			}
		}
	}
}

class RoundBtn implements Border 
{
    private int r;
    private int color;
    
    RoundBtn(int r, int color)
    {
        this.r = r;
        this.color=color;
    }
    public Insets getBorderInsets(Component c) 
    {
		return new Insets(this.r+1, this.r+1, this.r+2, this.r);
    }
    public boolean isBorderOpaque() 
    {
		return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) 
    {
    	if(color==0)
    		g.setColor(Color.BLACK);
    	else if(color==1)
    		g.setColor(Color.YELLOW);    	
    	else if(color==3) //For Winning
    		g.setColor(Color.GREEN);
    	else
    		g.setColor(Color.RED);
    	
    	g.fillRoundRect(x, y, width-1, height-1, r, r);
    }
}