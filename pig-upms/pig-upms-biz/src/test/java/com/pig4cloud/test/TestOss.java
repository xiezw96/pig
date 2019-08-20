package com.pig4cloud.test;

import com.pig4cloud.pig.PigAdminApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {PigAdminApplication.class, TestOss.class})
public class TestOss {
//    @Autowired
//    FileClient fileClient;
    
//    @Test
//    public void test1() {
//        String contents = "ABCDEFGDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD";
//        String downUri = fileClient.upload(contents.getBytes(), "test1.txt");
////        byte[] cBytes = fileClient.download(downUri);
////        Assert.assertEquals(contents, new String(cBytes));
//        fileClient.delete(downUri);
//    }
//
//    @Test
//    public void test2(){
//        String contents = "ABCDEFGDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD";
//        String downUri = fileClient.uploadByDomain(contents.getBytes(), "test1.txt","fx-open");
//        fileClient.delete(downUri);
//    }
}
