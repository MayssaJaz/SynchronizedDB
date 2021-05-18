package BO2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;
import com.google.gson.Gson;
import com.rabbitmq.client.*;
import com.rabbitmq.tools.json.JSONWriter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;




public class BO2 extends JFrame  {

    private static JTable myTable = new JTable(new MyTable());
	private static GestionSales bd = new GestionSales();

    
    MyButton button = new MyButton(myTable, 9);
     
    public BO2() throws Exception {
    	
        super("BO2");
        createGUI();
    }

    private void createGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 500));

        JButton addI = new JButton("+");
        addI.addActionListener(e -> ((MyTable)myTable.getModel()).setIncomeData(new Sale()));
        JButton synchro = new JButton("Synchronisation");
        synchro.addActionListener(e -> {
			try {
				sendData();
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
		});
        JPanel jpanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        jpanel.add(addI);
        jpanel.add(synchro);
        JScrollPane jscroll = new JScrollPane(myTable);
        add(jscroll, BorderLayout.CENTER);
        add(jpanel, BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);

        ((MyTable)myTable.getModel()).update();
    }
    
    
    private static void sendData() throws Exception{
    	ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (com.rabbitmq.client.Connection connection = factory.newConnection();
				Channel channel = connection.createChannel()) {
			channel.queueDeclare("HO2", false, false, false, null);
			JSONWriter rabbitmqJson = new JSONWriter();
			String jsonmessage = rabbitmqJson.write(((MyTable) myTable.getModel()).getIncomeData());
			channel.basicPublish("", "HO2", null, jsonmessage.getBytes());
			System.out.println(" [x] Sent '" + jsonmessage + "'");
		}
		
    	
    }
private static void verifier(Sale[] sales) {
		
		for(Sale s : sales) {
			
        	Sale sale = bd.find(s.getId());
        	//System.out.println(sale.getId());
        	if(sale == null) {
        		bd.addSale(s.getId(), s.getProduct(),s.getRegion() , s.getDate(), s.getQuantite(), s.getCost(), s.getAmt(),s.getTax(),s.getTotal());
        	}
        	else {
        		if (sale.hasChanged(s))
        			bd.updateSale(s);
        	}
        }
		Vector<Sale> sbd =  bd.selectAll();
		boolean there=false;
		if (sbd.size() > sales.length) {
			for ( Sale s : sbd) {
				there=false;
				for (Sale ss : sales) {
					if(s.getId()==ss.getId())
						there=true;
				}
				if(!there)
					bd.deleteSale(s.getId());
			}
		}
		((MyTable) myTable.getModel()).update();
		
	}

    public static void main(String[] args) throws Exception{
        SwingUtilities.invokeLater(() -> {
			try {
				new BO2().setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
        Gson gson = new Gson();
		  
		  
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    com.rabbitmq.client.Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.queueDeclare("BO2", false, false, false, null);
	    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
	    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
	        String message = new String(delivery.getBody(), "UTF-8");
	        Sale[] saless = gson.fromJson(message, Sale[].class);
	        verifier(saless);
	        
	    };
	    channel.basicConsume("BO2", true, deliverCallback, consumerTag -> { });
    }
}
