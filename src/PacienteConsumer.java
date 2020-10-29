import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class PacienteConsumer implements Runnable {

    @Override
    public void run() {
        try { // Create a connection factory.
            ActiveMQConnectionFactory factory =
                    new ActiveMQConnectionFactory("tcp://localhost:61616");

            factory.setTrustAllPackages(true);
            //Create connection.
            Connection connection = factory.createConnection();
            // Start the connection
            connection.start();
            // Cria a sessão
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //Crea a fila e informa qual o destinatário.
            Destination queue = session.createQueue("br.paciente");
            MessageConsumer consumer = session.createConsumer(queue);
            Message message = consumer.receive();
            if (message instanceof ObjectMessage) {
                ObjectMessage objectMessage = (ObjectMessage) message;
                Paciente paciente = (Paciente) objectMessage.getObject();
                System.out.println("Consumer Received: " + paciente.getNome());
            }
            session.close();
            connection.close();
        }
        catch (Exception ex) {
            System.out.println("Exception Occured");
        }
    }
}