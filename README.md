# Bin Packing Solver
I am a student from The Chinese University of Hong Kong (CUHK) major in System Engineering & Engineering Management and this is my final year project.
This is a Bin Packing Solver with our original algorithm developed in Java.
<br /><br />Our aim is to design and analyze an original alogrithm for both online and offline bin packing problem in 2D as well as comparing with other algorithms such as the following:
- Bottom-Left
- Best Short Side Fit
- Best Long Side Fit
- Best Area Fit
- Greedy Best long Side Fit
- Shelf First Fit
- Shelf Best Width Fit
- Shelf Worst Width Fit
- Shelf Best Area Fit

## Preview
**Offline Mode**
<br />![https://github.com/src/me/fyp/offline bin packing.gif](https://github.com/TTTANG216/BinPacking2/blob/master/src/me/fyp/offline%20bin%20packing.gif)
<br />
**Online Mode**
<br />![https://github.com/src/me/fyp/online bin packing.gif](https://github.com/TTTANG216/BinPacking2/blob/master/src/me/fyp/online%20bin%20packing.gif)

## Problem
**Two Dimensional Offline Bin Packing Problem:**\
The two dimensional offline bin packing problem is defined as the following:
<br />Given a positive integer bin with size $$h(C)\times w(C)$$ and a set of items $$L = \lbrace i_1, i_2, i_3, ..., i_n \rbrace$$,
each item $$i_k$$ having two integer $$h(i_k), w(i_k) \space \space s.t. 0 < h(i_k) \leq h(C),\space 0 < w(i_k) \leq w(C)$$.
Find the minimum integer $$m$$ such that there is a partition
```math
L = B_1 \cup B_2 \cup B_3 \cup ... \cup B_m \space s.t. \sum_{i_k∈B_j} h(i_k) \leq h(C), w(i_k) \leq w(C), 1 \leq j \leq m
```
**Two Dimensional Offline Bin Packing Problem:**\
The difference between offline and online is that the algorithm only knows the incoming item instead of all items in the set. Therefore, no sorting for the item set is allowed.

## Algorithm Pseudo Code
**Offline Algorithm**
The time complexity of our offline algorithm is $O(n^2)$
|Step|Description|Time Complexity|
| -------- | ------- | ------- |
|1. |Rotate all items $$i_k$$ in list $$L$$ such that $$h(i_k) > w(i_k), 0 \leq k < n$$ |$$O(n)$$|
|2. |Sort the list $$L$$ in descending order |$$O(nlogn)$$|
|3. |Do until size of list $$n = 0$$ |$$O(n^2)$$|
|3.1. |Do if bin $B_j$ has no item $i$ |$$O(n)$$|
|3.1.1. |Pack item $i_0$ to bin $B_j$ |$$O(1)$$|
|3.1.2. |Calculate remaining height, width $rh(C), rw(C)$ |$$O(1)$$|
|3.1.3. |Calculate upper remaining width $urw(C,i_0) |$$O(1)$$|
|3.1.4. |Remove item $i_0$ from the list $L, n←n-1$ |$$O(n)$$|
|3.2. |Otherwise do if $rh(C) \req h(i_k) |$$O(n)$$|
|3.2.1. |Do if $urw(B_j) \req w(i_k) |$$O(1)$$|
|3.2.1.1. |Pack item $i_k$ to bin $B_j$ |$$O(1)$$|
|3.2.1.2. |Calculate upper remaining width $urw(B_j,i_k)$ |$$O(1)$$|
|3.2.1.3. |Remove item $i_k$ from the list $L, n←n-1$ |$$O(n)$$|
|3.2.2. |Otherwise do |$$O(1)$$|
|3.2.2.1. |Do if $k + 2 > n$ |$$O(1)$$|
|3.2.2.1.1. |Set the upper bin $B_j$ is full and $k = 0$ |$$O(1)$$|
|3.2.2.2. |Otherwise set $k = k + 1$ |$$O(1)$$|
|3.3. |Otherwise do |$$O(n)$$|
|3.3.1 |Do if $rw(C) \geq w(i_k)$ |$$O(1)$$|
|3.3.1.1. |Pack item $i_k$ to bin $B_j$ |$$O(1)$$|
|3.3.1.2. |Do if $i_k > (C - rh(B_j))$ |$$O(n)$$|
|3.3.1.2.1. |Calculate upper remaining height $rh(C,i_k)$ |$$O(1)$$|
|3.3.1.3. |Calculate upper remaining width $urw((B_j, i_k)$ |$$O(1)$$|
|3.3.1.4. |Remove item $i_k$ from the list $L, n←n-1$ |$$O(n)$$|
|3.3.2. |Otherwise do |$$O(1)$$|
|3.3.2.1. |Set $k←k+1$ if $k + 2 < n$ |$$O(1)$$|
|3.3.2.2. |Otherwise set the bin $B_j$ is full and create a new bin $B_{j+1}$ |$$O(1)$$|
|4. |Return $j$ as the number of bin used |$$O(1)$$|

**Online Algorithm**
The time complexity of our online algorithm is $O(n^2)$
|Step|Description|Time Complexity|
| -------- | ------- | ------- |
|1. |Rotate all items $$i_k$$ in list $$L$$ such that $$h(i_k) > w(i_k), 0 \leq k < n$$ |$$O(n)$$|
|2. |Do until size of list $$n = 0$$ |$$O(n^2)$$|
|2.1. |Do if bin $B_j$ has no item $i$ |$$O(n)$$|
|2.1.1. |Pack item $i_0$ to bin $B_j$ |$$O(1)$$|
|2.1.2. |Calculate remaining height, width $rh(C), rw(C)$ |$$O(1)$$|
|2.1.3. |Calculate upper remaining width $urw(C,i_0) |$$O(1)$$|
|2.1.4. |Remove item $i_0$ from the list $L, n←n-1$ |$$O(n)$$|
|2.2. |Otherwise do if $rh(C) \req h(i_k) |$$O(n)$$|
|2.2.1. |Do if $urw(B_j) \req w(i_k) |$$O(1)$$|
|2.2.1.1. |Pack item $i_k$ to bin $B_j$ |$$O(1)$$|
|2.2.1.2. |Calculate upper remaining width $urw(B_j,i_k)$ |$$O(1)$$|
|2.2.1.3. |Remove item $i_k$ from the list $L, n←n-1$ |$$O(n)$$|
|2.2.2. |Otherwise set the bin $B_j$ is full and create a new bin $B_{j+1}$ |$$O(1)$$|
|2.3. |Otherwise do |$$O(n)$$|
|2.3.1 |Do if $rw(C) \geq w(i_k)$ |$$O(1)$$|
|2.3.1.1. |Pack item $i_k$ to bin $B_j$ |$$O(1)$$|
|2.3.1.2. |Do if $rw(B_j) > urw(B_j)$ |$$O(n)$$|
|2.3.1.2.1. |Calculate upper remaining height $rw(urw(B_j))$ |$$O(1)$$|
|2.3.1.3. |Calculate upper remaining width $urw((B_j, i_k)$ |$$O(1)$$|
|2.3.1.4. |Remove item $i_k$ from the list $L, n←n-1$ |$$O(n)$$|
|2.3.2. |Otherwise set the bin $B_j$ is full and create a new bin $B_{j+1}$ |$$O(1)$$|
|3. |Return $j$ as the number of bin used |$$O(1)$$|

Sorting a collection in java and removing an element from an arraylist takes $O(nlogn)$ and $O(n)$ time respectively.
<br /> The space complexity of our offline and online algorithm is $O(n)$ as we store all items in an arraylist.

<br /> More information can be view [here.](https://drive.google.com/file/d/1C-Oq-xnRjaQW9Mrh8F2u9gbJNtbDhxpq/view)
