import com.rabbitmq.client.*;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 读取队列的程序端，实现了Runnable接口。
 * @author syntx
 *
 */
public class QueueConsumer extends EndPoint implements Runnable, Consumer {

    public QueueConsumer(String endPointName) throws IOException, TimeoutException {
        super(endPointName);
        channel.exchangeDeclare("tttttttttttttttttttttttttt",BuiltinExchangeType.DIRECT);
        channel.queueBind(endPointName,"tttttttttttttttttttttttttt","key");
    }

    public void run() {
        try {
            //start consuming messages. Auto acknowledge messages.
            channel.basicConsume(endPointName, true,this);
        }catch (InterruptedIOException e){
            System.out.println("中断");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when consumer is registered.
     */
    public void handleConsumeOk(String consumerTag) {
        System.out.println("Consumer "+consumerTag +" registered");
    }


    public void handleCancel(String consumerTag) {}
    public void handleCancelOk(String consumerTag) {}
    public void handleRecoverOk(String consumerTag) {}

    @Override
    public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
        Map map = (HashMap) SerializationUtils.deserialize(bytes);
        System.out.println("Message Number "+ map.get("message number") + " received.");
    }

    public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {}
}