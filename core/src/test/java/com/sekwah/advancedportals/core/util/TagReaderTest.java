package com.sekwah.advancedportals.core.util;

import com.sekwah.advancedportals.core.serializeddata.DataTag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TagReaderTest {

    @Test
    public void testIsOpenStringReturnsTrueForUnclosedQuote() {
        String[] args = {"name:\"Hello"};
        assertTrue(TagReader.isOpenString(args));
    }

    @Test
    public void testIsOpenStringReturnsFalseForClosedQuote() {
        String[] args = {"name:\"Hello\""};
        assertFalse(TagReader.isOpenString(args));
    }

    @Test
    public void testIsOpenStringHandlesMultiArgQuote() {
        String[] args = {"name:\"Hello", "World\""};
        assertFalse(TagReader.isOpenString(args));
    }

    @Test
    public void testGetTagsFromArgsParsesSimpleTag() {
        String[] args = {"name:Test"};
        ArrayList<DataTag> tags = TagReader.getTagsFromArgs(args);
        assertEquals(1, tags.size());
        DataTag tag = tags.getFirst();
        assertEquals("name", tag.NAME);
        assertArrayEquals(new String[]{"Test"}, tag.VALUES);
    }

    @Test
    public void testGetTagsFromArgsParsesQuotedValue() {
        String[] args = {"name:\"Test", "Portal\"", "dest:spawn"};
        ArrayList<DataTag> tags = TagReader.getTagsFromArgs(args);
        assertEquals(2, tags.size());

        DataTag nameTag = tags.stream().filter(t -> t.NAME.equals("name")).findFirst().orElse(null);
        DataTag destTag = tags.stream().filter(t -> t.NAME.equals("dest")).findFirst().orElse(null);
        assertNotNull(nameTag);
        assertNotNull(destTag);
        assertArrayEquals(new String[]{"Test Portal"}, nameTag.VALUES);
        assertArrayEquals(new String[]{"spawn"}, destTag.VALUES);
    }

    @Test
    public void testGetTagsFromArgsParsesMultipleValues() {
        String[] args = {"dest:world", "dest:nether"};
        ArrayList<DataTag> tags = TagReader.getTagsFromArgs(args);
        assertEquals(1, tags.size());
        DataTag destTag = tags.get(0);
        assertEquals("dest", destTag.NAME);
        assertArrayEquals(new String[]{"world", "nether"}, destTag.VALUES);
    }
}

