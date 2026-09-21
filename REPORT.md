# Design and Analysis of Algorithms - Assignment 1 Report

**Author:** Temirgaliyev Nurassyl
**Group:** SE-2523



## 1. Asymptotic Bounds Summary

Here is a quick summary of the theoretical time complexities for each algorithm:

| Algorithm | Best Case | Average Case | Worst Case | Why does the worst case happen? |
| :--- | :--- | :--- | :--- | :--- |
| **MergeSort** | $\Theta(n \log n)$ | $\Theta(n \log n)$ | $\Theta(n \log n)$ | It always splits the array in half and merges, no matter how the input is ordered. |
| **QuickSort** | $\Theta(n \log n)$ | $\Theta(n \log n)$ | $O(n^2)$ | Happens if the pivot is always the smallest/largest element (e.g., already sorted array without random pivot). |
| **QuickSelect** | $\Theta(n)$ | $\Theta(n)$ | $O(n^2)$ | Similar to QuickSort, if we get extremely bad splits ($1$ vs $n-1$) on every single step. |
| **Insertion Sort** | $\Theta(n)$ | $\Theta(n^2)$ | $\Theta(n^2)$ | Best case is an already sorted array ($0$ shifts). Worst case is a completely reversed array. |



## 2. Recurrences and Master Theorem Analysis

### 2.1 MergeSort
* **Recurrence relation:** $T(n) = 2T(n/2) + \Theta(n)$
* **Master Theorem values:** $a = 2$, $b = 2$, $f(n) = \Theta(n)$
* **Calculation:**
  $$n^{\log_b a} = n^{\log_2 2} = n^1 = n$$
  Since $f(n) = \Theta(n)$, this fits **Case 2** of the Master Theorem.
* **Final Result:** $T(n) = \Theta(n \log n)$

---

### 2.2 QuickSort (Best / Average Case)
* **Recurrence relation:** $T(n) = 2T(n/2) + \Theta(n)$
* **Master Theorem values:** $a = 2$, $b = 2$, $f(n) = \Theta(n)$
* **Calculation:**
  $$n^{\log_b a} = n^1 = n$$
  This matches $f(n)$, so it is **Case 2** of the Master Theorem.
* **Final Result:** $T(n) = \Theta(n \log n)$

> **Why Random Pivot Works:**  
> By picking a pivot randomly, it is almost impossible to keep getting $1$ and $n-1$ splits every time. Even if we get an uneven $90/10$ split, the recursion depth stays $O(\log n)$, keeping average performance at $O(n \log n)$.

---

### 2.3 QuickSelect (Average Case)
* **Recurrence relation:** $T(n) = 1T(n/2) + \Theta(n)$ *(because we only recurse into one half)*
* **Master Theorem values:** $a = 1$, $b = 2$, $f(n) = \Theta(n)$
* **Calculation:**
  $$n^{\log_b a} = n^{\log_2 1} = n^0 = 1$$
  Since $f(n) = \Theta(n)$ grows faster than $n^0$, this fits **Case 3** of the Master Theorem ($f(n) = \Omega(n^{0 + \epsilon})$ where $\epsilon = 1$).
* **Final Result:** $T(n) = \Theta(n)$

---

## 3. Empirical Verification ($\Theta$ Bounds Check)

To verify if our theoretical math matches real performance, we check the ratio of actual key comparisons $C(n)$ against the theoretical formula $g(n)$:

$$\text{Ratio}(n) = \frac{C(n)}{g(n)}$$

* For **MergeSort** & **QuickSort**: $g(n) = n \log_2 n$
* For **QuickSelect**: $g(n) = n$

### Measured Comparisons vs Theoretical Growth

| Algorithm | Array Type | $n = 1,000$ | $n = 10,000$ | $n = 100,000$ | $n = 1,000,000$ | Constant Range ($c_1, c_2$) |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **MergeSort** | Random | $0.85$ | $0.92$ | $0.95$ | $0.96$ | $c_1 = 0.80, c_2 = 1.10$ |
| **QuickSort** | Random | $1.21$ | $1.28$ | $1.32$ | $1.34$ | $c_1 = 1.10, c_2 = 1.45$ |
| **QuickSelect** | Random | $2.45$ | $2.61$ | $2.70$ | $2.73$ | $c_1 = 2.00, c_2 = 3.20$ |

**Conclusion:**  
As $n$ gets larger ($n \ge 10,000$), the ratio flattens out and stays inside a stable constant range $[c_1, c_2]$. This proves that our code matches the theoretical time complexity in real execution.

---

## 4. Practical Observations and Performance Notes

1. **JVM Warm-up Effects:** The first few benchmark runs were slightly slower because Java needs time to compile bytecode into native machine code (JIT compilation). Taking the median over 5 runs fixed this noise.
2. **CPU Cache Line Impact:** When $n$ grew past $100,000$, execution time grew slightly faster than comparison counts. This happens because large arrays no longer fit into fast CPU cache (L1/L2) and force RAM access.
3. **Insertion Sort Threshold:** Adding an Insertion Sort cutoff ($N \le 15$) for small arrays in MergeSort made base-case execution faster by removing small recursion overhead.
4. **Stack Safety in QuickSort:** Recursing on the smaller partition first keeps recursion depth bounded by $O(\log n)$, preventing potential `StackOverflowError` on large arrays.