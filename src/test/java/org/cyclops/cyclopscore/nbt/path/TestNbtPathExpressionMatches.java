package org.cyclops.cyclopscore.nbt.path;

import net.minecraft.nbt.StringNBT;
import org.cyclops.cyclopscore.nbt.path.parse.NbtPathExpressionExecutionContext;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestNbtPathExpressionMatches {

    private Stream<NbtPathExpressionExecutionContext> stream;
    private NbtPathExpressionMatches matches;

    @Before
    public void beforeEach() {
        stream = Stream.of(
                new NbtPathExpressionExecutionContext(new StringNBT("a")),
                new NbtPathExpressionExecutionContext(new StringNBT("b")),
                new NbtPathExpressionExecutionContext(new StringNBT("c"))
        );
        matches = new NbtPathExpressionMatches(stream);
    }

    @Test
    public void testGetMatches() {
        assertThat(matches.getContexts(), is(stream));
    }

    @Test
    public void testForAll() {
        matches = NbtPathExpressionMatches.forAll(
                new NbtPathExpressionExecutionContext(new StringNBT("a")),
                new NbtPathExpressionExecutionContext(new StringNBT("b")),
                new NbtPathExpressionExecutionContext(new StringNBT("c"))
        );
        assertThat(matches.getContexts().collect(Collectors.toList()), equalTo(stream.collect(Collectors.toList())));
    }

}
