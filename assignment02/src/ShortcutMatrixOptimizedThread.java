public class ShortcutMatrixOptimizedThread implements Runnable{
    int start;
    int end;
    float[][] rows;
    float[][] columns;
    float[][] var2;

    public ShortcutMatrixOptimizedThread(int start, int end, float[][] rows, float[][] columns, float[][] var2) {
        this.start = start;
        this.end = end;
        this.rows = rows;
        this.columns = columns;
        this.var2 = var2;
    }
    @Override
    public void run() {
        for(int var3 = start; var3 < end; ++var3) {
            for(int var4 = 0; var4 < this.rows.length; ++var4) {
                float var5 = Float.MAX_VALUE;

                for(int var6 = 0; var6 < this.rows.length; ++var6) {
                    float var7 = this.rows[var3][var6];
                    float var8 = this.columns[var4][var6];
                    float var9 = var7 + var8;
                    if (var9 < var5) {
                        var5 = var9;
                    }
                }

                var2[var3][var4] = var5;
            }
        }
    }
}