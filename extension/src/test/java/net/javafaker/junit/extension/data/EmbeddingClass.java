package net.javafaker.junit.extension.data;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EmbeddingClass {
    private Long id;
    private SimpleClass value;
}
