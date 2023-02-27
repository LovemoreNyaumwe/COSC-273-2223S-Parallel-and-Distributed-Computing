# COSC-273-2223S-Parallel-and-Distributed-Computing Assignment 02

Submission includes a README file indicating
(1) the main optimizations made over the baseline implementation,
The main optimization was transposing the matrix in order to make use of spatial locality property when accessing columns. I also parallelized the outer main loop to order to implement multithreading.
(2) the effect of these optimizations (individually and together), and
The effect was a 20 times faster implementation of large values and a double increment in the speed for smaller values.
(3) any other optimizations tested, whether they had a positive, negative, or insignificant effect on the program performance. The document should be concise (not more than a couple short paragraphs are necessary), but it should give an indication of the optimizations you tested, what worked, and what didn’t.
I made use of 2 cores to run the program. I tried to run the program on all my computer’s 12 cores but the result was not faster. The result stopped getting faster on the 3rd thread.
