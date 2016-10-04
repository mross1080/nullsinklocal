package io.mross.MyNullSink;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.Bindings;
import org.springframework.cloud.stream.binder.BinderFactory;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.integration.channel.NullChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.*;
import java.util.UUID;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MyNullSinkApplication.class, properties= {"logging.level.root=DEBUG"})
@WebAppConfiguration
@DirtiesContext
@TestPropertySource("classpath:application-test.properties")
public class MyNullSinkApplicationTests {

    private static Logger logger = LoggerFactory.getLogger(NullChannel.class);

    @Autowired
    @Bindings(MyNullSinkConfiguration.class)
    private Sink sink;


    private static final String TMPDIR = System.getProperty("java.io.tmpdir");

    private static final String ROOT_DIR = TMPDIR + File.separator + "dataflow-tests.txt";
    

    @Before
    public void setup() {
        File file = new File(ROOT_DIR);
        try {
            if (file.exists()) {
                file.delete();
                System.out.println(file.getName() + " Deleted");
            }
        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void testNullSink() throws FileNotFoundException {

        File file = new File(ROOT_DIR);
        file.deleteOnExit();
        assertFalse(file.exists());
        String uniqueId = UUID.randomUUID().toString();
        System.out.println(uniqueId);


        PrintStream out = new PrintStream(new FileOutputStream(ROOT_DIR));
        System.setOut(out);

        out.close();
        BufferedReader br = null;

        Boolean foundNullChannelOutPutFlag = false;
        assertTrue(file.exists());

        try {

            String sCurrentLine;
            br = new BufferedReader(new FileReader(ROOT_DIR));

            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.contains("message sent to null channel: GenericMessage [payload=" + uniqueId)) {
                    foundNullChannelOutPutFlag  = true;
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }

        assertTrue(foundNullChannelOutPutFlag);



    }



}
