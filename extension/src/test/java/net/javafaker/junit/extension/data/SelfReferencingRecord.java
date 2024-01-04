package net.javafaker.junit.extension.data;

public record SelfReferencingRecord (Long id, String name, SelfReferencingRecord child){
}
