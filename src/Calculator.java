import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;
public class Calculator {
    int boardwidth = 360;
    int boardheight = 540;

    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);

    String[] ButtonValues={
        "AC","+/-","%","/",
        "7","8","9","x",
        "4","5","6","-",
        "1","2","3","+",
        "0",".","√","=",    
    };
    String[] rightSymbols={"/","x","-","+","="};
    String[] topSymbols={"AC","+/-","%"};

    JFrame frame=new JFrame("Calculator");
    JLabel displayLabel=new JLabel();   
    JPanel displayPanel=new JPanel();
    JPanel buttonPanel=new JPanel();

    String A="0";
    String operator=null;
    String B=null;

    public Calculator() {
        //frame.setVisible(true);
        frame.setSize(boardwidth,boardheight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial",Font.PLAIN,80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel,BorderLayout.NORTH);

        buttonPanel.setLayout(new GridLayout(5,4));
        buttonPanel.setBackground(customBlack);
        frame.add(buttonPanel);

        for(int i=0;i<ButtonValues.length;i++)
        {
            JButton button=new JButton();
            String buttonValue=ButtonValues[i];
            button.setFont(new Font("Arial",Font.PLAIN,30));
            button.setText(buttonValue);
            button.setFocusPainted(false);
            button.setBorder(new LineBorder(customBlack));
            if(Arrays.asList(topSymbols).contains(buttonValue)){
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            }
            else if(Arrays.asList(rightSymbols).contains(buttonValue)){
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            }
            else{
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }
            buttonPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    JButton button=(JButton)e.getSource();
                    String buttonValue=button.getText();
                    if(Arrays.asList(rightSymbols).contains(buttonValue)){
                        if(buttonValue=="="){
                            if(A!=null){
                                B=displayLabel.getText();
                                double numA=Double.parseDouble(A);
                                double numB=Double.parseDouble(B);

                                if(operator=="+"){
                                    double result=numA+numB;
                                    displayLabel.setText(removeZeroDecimal(result));
                                }
                                else if(operator=="-"){
                                    double result=numA-numB;
                                    displayLabel.setText(removeZeroDecimal(result));
                                }
                                else if(operator=="x"){
                                    double result=numA*numB;
                                    displayLabel.setText(removeZeroDecimal(result));
                                }
                                else if(operator=="/"){
                                    double result=numA/numB;
                                    displayLabel.setText(removeZeroDecimal(result));
                                }
                                clearAll();
                            }
                        }
                        else if("/x-+".contains(buttonValue)){
                            if(operator==null){
                                A=displayLabel.getText();
                                displayLabel.setText("0");
                                B="0";
                            }
                            operator=buttonValue;
                        }
                    }
                    else if(Arrays.asList(topSymbols).contains(buttonValue)){
                        if(buttonValue=="AC"){
                            clearAll();
                            displayLabel.setText("0");
                        }
                        else if(buttonValue=="+/-"){
                            double numDisplay=Double.parseDouble(displayLabel.getText());
                            numDisplay*=-1;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                        else if(buttonValue=="%"){
                            double numDisplay=Double.parseDouble(displayLabel.getText());
                            numDisplay/=100;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                    }
                    else{
                        if (buttonValue.equals("√")) {
                            String text = displayLabel.getText();
                            if (text.equals("") || text.equals("Error")) {
                                displayLabel.setText("0");
                                clearAll();
                                return;
                            }
                            double numDisplay = Double.parseDouble(text);
                            if (numDisplay < 0) {
                                displayLabel.setText("Error");
                                clearAll();
                                return;
                            }
                            double result = Math.sqrt(numDisplay);
                            displayLabel.setText(removeZeroDecimal(result));
                            return;
                        }
                        if(buttonValue=="."){
                            if(!displayLabel.getText().contains(buttonValue)){
                                displayLabel.setText(displayLabel.getText()+buttonValue);
                            }
                        }
                        else if("0123456789".contains(buttonValue)){
                            if(displayLabel.getText()=="0"){
                                displayLabel.setText(buttonValue);
                            }
                            else{
                                displayLabel.setText(displayLabel.getText()+buttonValue);
                            }
                        }
                    }
                }
            });
            frame.setVisible(true);
        }
    }
    void clearAll(){
        A="0";
        operator=null;
        B=null;
    }
    String removeZeroDecimal(double nums){
        if(nums%1==0){
            return Integer.toString((int)nums);
        }
        return Double.toString(nums);
    }
}
