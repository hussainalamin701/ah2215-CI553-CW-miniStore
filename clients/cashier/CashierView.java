package clients.cashier;

import catalogue.Basket;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;


/**
 * View of the model 
 */
public class CashierView implements Observer
{
  private static final int H = 350;       // Height of window pixels
  private static final int W = 400;       // Width  of window pixels
  
  private static final String CHECK  = "Check";
  private static final String BUY    = "Buy";
  private static final String BOUGHT = "Bought/Pay";
  private static final String DISCOUNT = "Discount";
  private static final String CLEAR = "Clear";

  private final JLabel      pageTitle  = new JLabel();
  private final JLabel      theAction  = new JLabel();

  private final JTextField  theInput   = new JTextField();
  private final JTextField buyMany = new JTextField();
  private final JTextArea   theOutput  = new JTextArea();

  private final JScrollPane theSP      = new JScrollPane();

  private final JButton     theBtCheck = new JButton( CHECK );
  private final JButton     theBtBuy   = new JButton( BUY );
  private final JButton     theBtBought= new JButton( BOUGHT );
  private final JButton     theBtDiscount  = new JButton( DISCOUNT );
  private final JButton     theBtClear = new JButton( CLEAR );

  private StockReadWriter theStock     = null;
  private OrderProcessing theOrder     = null;
  private CashierController cont       = null;
  
  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-coordinate of position of window on screen 
   * @param y     y-coordinate of position of window on screen  
   */
          
  public CashierView(  RootPaneContainer rpc,  MiddleFactory mf, int x, int y  )
  {
    try                                           // 
    {      
      theStock = mf.makeStockReadWriter();        // Database access
      theOrder = mf.makeOrderProcessing();        // Process order
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    Container cp         = rpc.getContentPane();    // Content Pane
    Container rootWindow = (Container) rpc;         // Root Window
    cp.setLayout(null);                             // No layout manager
    rootWindow.setSize( W, H );                     // Size of Window
    rootWindow.setLocation( x, y );

    rootWindow.setBackground( Color.black );
    cp.setBackground(new Color(0x70798C));

    Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is
    Font titleFont = new Font("Arial", Font.BOLD, 12);

    theOutput.setBackground(Color.white); // Set the output text area background.

    pageTitle.setBounds( 110, 0 , 270, 20 );

    pageTitle.setFont(titleFont);
    pageTitle.setText( "Thank You for Shopping at MiniStore" );
    cp.add( pageTitle );  
    
    theBtCheck.setBounds( 16, 25+60*0, 80, 40 );    // Check Button
    theBtCheck.setToolTipText("Check the product details by entering its code.");
    theBtCheck.addActionListener(                   // Call back code
      e -> cont.doCheck(theInput.getText(), Integer.parseInt(buyMany.getText())) );
    cp.add( theBtCheck );                           //  Add to canvas

    theBtBuy.setBounds( 16, 25+60*1, 80, 40 );      // Buy button 
    theBtBuy.addActionListener(                     // Call back code
      e -> cont.doBuy() );
    cp.add( theBtBuy );                             //  Add to canvas

    theBtDiscount.setBounds( 16, 25+60*2, 80, 40 );
    theBtDiscount.addActionListener(
            e -> cont.doDiscount(theInput.getText()) );
    cp.add( theBtDiscount );

    theBtBought.setBounds( 16, 25+60*3, 80, 40 );   // Bought Button
    theBtBought.addActionListener(                  // Call back code
      e -> cont.doBought() );
    cp.add( theBtBought );                          //  Add to canvas

    theBtClear.setBounds( (80 * 4) - 25, 25+60*4, 80, 40 );
    theBtClear.setBackground(Color.cyan);
    theBtClear.addActionListener(
            e -> cont.doClear() );
    cp.add( theBtClear );

    theAction.setBounds( 110, 25 , 160, 20 );       // Message area
    theAction.setText( "" );                        // Blank
    cp.add( theAction );                            //  Add to canvas

    buyMany.setBounds( 300, 50, 80, 40 );         // Buy Quantity Area
    buyMany.setText("1");                           // Blank
    buyMany.setFont( f );
    cp.add( buyMany );                              // Add to canvas

    theInput.setBounds( 110, 50, 170, 40 );         // Input Area
    theInput.setText("");                           // Blank
    cp.add( theInput );                             //  Add to canvas

    theSP.setBounds( 110, 100, 270, 145 );          // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea
    rootWindow.setVisible( true );                  // Make visible
    theInput.requestFocus();                        // Focus is here
  }

  /**
   * The controller object, used so that an interaction can be passed to the controller
   * @param c   The controller
   */

  public void setController( CashierController c )
  {
    cont = c;
  }

  /**
   * Update the view
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
  @Override
  public void update( Observable modelC, Object arg )
  {
    CashierModel model  = (CashierModel) modelC;
    String      message = (String) arg;
    theAction.setText( message );
    Basket basket = model.getBasket();
    if ( basket == null )
      theOutput.setText( "Customers order" );
    else
      theOutput.setText( basket.getDetails() );
    
    theInput.requestFocus();               // Focus is here
  }

}
