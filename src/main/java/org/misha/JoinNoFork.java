package org.misha;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public abstract class JoinNoFork<Q1, Q2, T extends Modifiable<Q1, Q2>> {
    private final CompletableFuture<List<T>> futureEntities;

    public JoinNoFork(List<T> entities) {
        this.futureEntities = CompletableFuture.supplyAsync(() -> entities);
    }

    public void main() {
        long start = System.currentTimeMillis();
        futureEntities.thenCompose((List<T> entities) -> {
            List<CompletableFuture<T>> futureUpdatedEntities = entities.stream().map((T entity) -> {
                Q1 propertyOne = entity.getDoubleProperty();
                return futureMapPropertyOneToPropertyTwo(propertyOne, entity).thenApply((Q2 r) -> {
                    entity.modifyUsing(r);
                    return entity;
                });
            }).collect(Collectors.toList());

            return CompletableFuture.allOf(
                    futureUpdatedEntities.toArray(new CompletableFuture[futureUpdatedEntities.size()]))
                                    .thenApply((Void v) -> futureUpdatedEntities.stream()
                                                                                .map(CompletionStage::toCompletableFuture)
                                                                                .map(CompletableFuture::join)
                                                                                .collect(Collectors.toList()));
        });
        futureEntities.whenComplete((List<T> entities, Throwable throwableBiConsumer) -> {
            if (throwableBiConsumer == null) {
                entities.forEach(System.out::println);
            } else {
                throw new RuntimeException(throwableBiConsumer);
            }
        });
        futureEntities.toCompletableFuture().join();
        long end = System.currentTimeMillis();
        System.out.println("Took " + (end - start) + " ms.");
    }

    private CompletableFuture<Q2> futureMapPropertyOneToPropertyTwo(Q1 propertyOne, T t) {
        return CompletableFuture.supplyAsync(() -> by(propertyOne, t)).exceptionally(th -> null);
    }

    abstract Q2 by(Q1 q1, T t);
}
