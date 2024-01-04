package net.javafaker.junit.extension.data;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmbeddingBuilderClass {
    private Long id;
    private SimpleBuilderClass value;
}
