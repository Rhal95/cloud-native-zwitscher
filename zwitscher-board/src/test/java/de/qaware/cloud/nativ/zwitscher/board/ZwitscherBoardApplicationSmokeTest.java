package de.qaware.cloud.nativ.zwitscher.board;

import de.qaware.cloud.nativ.zwitscher.board.web.ZwitscherBoardController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ZwitscherBoardApplicationSmokeTest {

    @Autowired
    ZwitscherBoardController controller;

    @Test
    public void contextLoad() {
        assertThat(controller).isNotNull();
    }
}