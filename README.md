# QuickSort_ExternalFile
implement a sorting algorithm on a file. The input data file will consist of many 4-byte
records, with each record consisting of two 2-byte (short) integer values in the range 1 to 30,000.
The first 2-byte field is the key value (used for sorting) and the second 2-byte field contains a data
value. The input file is guaranteed to be a multiple of 4096 bytes. All I/O operations will be done
on blocks of size 4096 bytes (i.e., 1024 logical records).
sort the fle (in ascending order), using a modified version of Quicksort. The
modification comes in the interaction between the Quicksort algorithm and the file storing the data.
The array being sorted will be the file itself, rather than an array stored in memory. All accesses
to the file will be mediated by a bufier pool. The buffer pool will store 4096-byte blocks (1024
records). The bufier pool will be organized using the Least Recently Used (LRU) replacement
scheme. See OpenDSA Module 9.4 for more information about buffer pools.
