package HomeWork;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        firstMethod();
        secondMethod();
    }

    public static void firstMethod() {
        int size = 10_000_000;
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("One thread time: " + (System.currentTimeMillis() - startTime) + " ms.");
    }

    public static void secondMethod() throws InterruptedException {
        int size = 10_000_000;
        int h = size / 2;
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis();
        float[] arr1 = new float[h];
        System.arraycopy(arr, 0, arr1, 0, h);
        float[] arr2 = new float[h];
        System.arraycopy(arr, h, arr2, 0, h);

        Thread thread1 = new Thread(() -> {
            for(int i = 0; i < h; ++i){
                arr1[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        Thread thread2 = new Thread(() -> {
            for(int i = h; i < size; ++i){
                arr2[i - 5_000_000] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);

        System.out.println("Two thread time: " + (System.currentTimeMillis() - startTime) + " ms.");
    }
}
