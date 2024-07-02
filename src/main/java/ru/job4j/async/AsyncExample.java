package ru.job4j.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AsyncExample {

    private static void work(String name) throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println(name + " работает");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    // метод runAsync просто выполнит действие
    public static CompletableFuture<Void> goToTrash(String name) {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println(name + " пошел выносить мусор");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(name + " вернулся!");
                }
        );
    }

    // метод supplyAsync вернет результат
    public static CompletableFuture<String> buyProduct(String name, String product) {
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println(name + " идет в магазин");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(name + ": " + product + " куплено");
                    return product;
                }
        );
    }

    //метод thenRun ничего не возвращает, а позволяет выполнить задачу типа Runnable после выполнения асинхронной задачи
    public static void runAsyncExampleWithTrash() throws Exception {
        CompletableFuture<Void> gtt = goToTrash("Сын");
        gtt.thenRun(() -> {
            int count = 0;
            while (count < 3) {
                System.out.println("Сын моет руки");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
            }
            System.out.println("Сын помыл руки");
        });
        work("Папа");
    }

    // не запускаем отдельную задачу, а просто выполняем действие (уборку продукта в холодильник)
    // метод thenAccept имеет доступ к результату CompletableFuture
    public static void runAsyncExampleWithProductRemove() throws Exception {
        CompletableFuture<String> bm = buyProduct("Сын", "Молоко");
        bm.thenAccept((product) -> System.out.println("Сын убрал " + product + " в холодильник "));
        work("Папа");
        System.out.println("Куплено: " + bm.get());
    }

    // производим преобразование полученного результата
    public static void runAsyncExampleWithProductGive() throws Exception {
        CompletableFuture<String> bm = buyProduct("Сын", "Молоко")
                .thenApply((product) -> "Сын налил в кружку " + product);
        work("Папа");
        System.out.println(bm.get());
    }

    //сначала должен выполниться вынос мусора, а только потом покупка продукта
    public static void thenComposeExample() throws Exception {
        CompletableFuture<String> result = goToTrash("Сын").thenCompose(a -> buyProduct("Сын", "Milk"));
        result.get();
        work("Папа");
    }

    // действия могут быть выполнены независимо друг от друга (посылаем двух сыновей по разным делам)
    public static void thenCombineExample() throws Exception {
        CompletableFuture<String> result = buyProduct("Старший сын", "Молоко")
                .thenCombine(buyProduct("Младший сын", "Хлеб"), (r1, r2) -> "Куплены " + r1 + " и " + r2);
        work("Папа");
        System.out.println(result.get());
    }

    public static CompletableFuture<Void> washHands(String name) {
        return CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " моет руки");
        });
    }

    // метод allOf обеспечивает выполнение всех задач
    public static void allOfExample() throws Exception {
        CompletableFuture<Void> all = CompletableFuture.allOf(
                washHands("Папа"), washHands("Мама"),
                washHands("Ваня"), washHands("Боря")
        );
        TimeUnit.SECONDS.sleep(3);
    }

    public static CompletableFuture<String> whoWashHands(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return name + " моет руки";
        });
    }

    // метод anyOf возвращает первую выполненную задачу
    public static void anyOfExample() throws Exception {
        CompletableFuture<Object> first = CompletableFuture.anyOf(
                whoWashHands("Папа"), whoWashHands("Мама"),
                whoWashHands("Ваня"), whoWashHands("Боря")
        );
        System.out.println("Кто сейчас моет руки?");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(first.get());
    }

    public static void main(String[] args) throws Exception {
//        anyOfExample();
        runAsyncExampleWithProductGive();
    }

}