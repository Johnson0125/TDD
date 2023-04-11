package org.learn.org;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class ArgsTest {
    // -l -p 8081 -d /user/logs
    // [-l],[-p, 8081], [-d, /user/logs]
    // {-l:[], -p:[8080], -d:[/user/logs]}

    // single path:
    //     -bool -l true/false
    @Test
    public void given_boolean_then_pass() {
        BooleanOption option = Args.parse(BooleanOption.class, "-l");
        assert option != null;
        assertTrue(option.logging());
    }

    @Test
    @Disabled
    public void given_false_then_false() {
        BooleanOption option = Args.parse(BooleanOption.class, "-l", "false");
        assertFalse(option.logging());
    }

    @Test
    public void when_no_l_then_false() {
        BooleanOption option = Args.parse(BooleanOption.class, "");
        assertFalse(option.logging());
    }

    static record BooleanOption(@Option("l") boolean logging) {
    }


    //     -int -p 8080
    @Test
    public void given_port_then_pass() {
        IntOption option = Args.parse(IntOption.class, "-p", "8080");
        assertEquals(8080, option.port());
    }

    static record IntOption(@Option("p") int port) {

    }
    //      -string -d /user/logs

    @Test
    public void given_directory_then_pass() {
        StringOption option = Args.parse(StringOption.class, "-d", "/usr/logs");
        assertEquals("/usr/logs", option.directory());

    }

    record StringOption(@Option("d") String directory) {

    }

    // multiple -path:
    //   -l -p 8081 -d /user/logs
    @Test
    public void given_multiple_then_pass() {
        MultipleOption option = Args.parse(MultipleOption.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertEquals(true, option.logging());
        assertEquals(8080, option.port());
        assertEquals("/usr/logs", option.directory());
    }

    record MultipleOption(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {

    }

    // sad path:
    //TODO     -bool -l t
    //TODO     -int -p df | -p 80 90
    //TODO     -string -d 4 5 9


    //TODO   -g this is a list -d 1 2 -3 5

    //TODO default value
    // -bool : false
    // -int :0
    // string:""





}
