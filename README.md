# cassandra-sim
Implementing similarity search in Cassandra

1 - Create new partitioner
    - Copy an existing partitioner (RandomPartitioner) renaming it
    - Rebuild the application
    - Starts application (delete directory data/ before starts)

SimilarityPartioner (implemented)
  RandomyperplaneHash
  BinaryReflectedGrayCode
  BinaryType (implemented)
    BinarySerializer (implemented)
