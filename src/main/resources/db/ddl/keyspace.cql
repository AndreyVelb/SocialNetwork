CREATE KEYSPACE IF NOT EXISTS social_network
WITH REPLICATION = {
        'class' : 'SimpleStrategy', 'replication_factor' : 1
        };