#This project was moved to the pluxos group:

[cassandra-sim pluxos](https://github.com/pluxos/cassandra-sim)

# cassandra-sim
Implementing similarity search in Cassandra

## SimilarityPartitioner
Implementa a interface IPartitioner, responsável por gerar os Tokens baseados no tipo de particionamento escolhido.

### RandomHyperplaneHash
Implementa o hash baseado em similaridade.

### BinaryReflectedGrayCode
Implementa a forma de ordenação baseado em código de Hamming.

### BinaryType
Implementa o tipo do Token.

#### BinarySerializer
Implementa o tipo de serialização baseado no Token.

#### CQL3Type.Native
Foi feita uma alteração, adicionando ao enum a linha "BINARY      (BinaryType.instance),".

## cassandra.yaml
Adicionado duas propriedades para o calculo do rhh  
identifier_length - o tamanho do identificador gerado  
vectors - os vetores aleatórios

## Config
Adicionado duas propriedades para o calculo do rhh  
identifier_length - o tamanho do identificador gerado  
vectors - os vetores aleatórios

## DatabaseDescriptor
Adicionado os métodos get das propriedades do rhh
