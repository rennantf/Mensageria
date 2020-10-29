import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class PacienteProducer implements Runnable {

    public void run() {
        try { // Create a connection factory.
            ActiveMQConnectionFactory factory =
                    new ActiveMQConnectionFactory("tcp://localhost:61616");

            //Create connection.
            Connection connection = factory.createConnection();

            // Start the connection
            connection.start();

            // Create a session which is non transactional
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create Destination queue
            Destination queue = session.createQueue("br.paciente");

            // Create a producer
            MessageProducer producer = session.createProducer(queue);

            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            Paciente paciente = new Paciente();
            paciente.setId(1);
            paciente.setNome("Rennan Temoteo");

            // insert message
            ObjectMessage message = session.createObjectMessage(paciente);

            System.out.println("Paciente message enviado: " + paciente.getId());
            producer.send(message);

            session.close();
            connection.close();
        }
        catch (Exception ex) {
            System.out.println("Exception Occured");
            ex.printStackTrace();
        }
    }
}
