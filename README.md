Software Transactional Memory (STM)

Software transactional memory is a concurrency control mechanism for controling access to shared data. 
It is all done by software, and using the java implementation of STM it was possible to create an example. This java library is called 
Multiverse, and provides some features for implementing software transactional memory in Java.

The code example has two folders: stm_race_conditions and stm_deadlocks

 the first folder: stm_race_conditions 
it contains 3 packages: race_conditions, lock_based_synchronization, and transactional_memory. Each package has its main method and can be executed alone. 
race_conditions package contains the code which shows the occurrence of race conditions; 
lock_based_synchronisation package contains the code which shows how lock-based synchronization can be used to prevent the occurrence of race conditions. finally, the transactional_memory package contains multiverse library and simply shows the concept of STM prevents the occurrences of race conditions 

the second folder: stm_deadlocks
it consists of two packages: deadlock_lbs and deadlocks_stm. again, each package has its main method can be executed alone.
deadlock_lbs package shows how deadlocks can occur when lock-based synchronisation is used 
deadlock_stm package shows how deadlocks do not occur when the STM approach is used
