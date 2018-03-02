CREATE KEYSPACE cos WITH replication = {
  'class': 'NetworkTopologyStrategy',
  'DC1': '1'
};

USE cos;

CREATE TABLE counts (
  id text PRIMARY KEY,
  container_count counter,
  object_bytes counter,
  object_count counter
) WITH compaction = {'class': 'LeveledCompactionStrategy'}
    AND gc_grace_seconds = 172800;

CREATE TABLE listing (
  id text,
  bucket int,
  child text,
  child_id text,
  PRIMARY KEY ((id, bucket), child)
) WITH compaction = {'class': 'LeveledCompactionStrategy'}
    AND gc_grace_seconds = 172800;

CREATE TABLE metadata (
  id text PRIMARY KEY,
  doc text
) WITH compaction = {'class': 'LeveledCompactionStrategy'}
    AND gc_grace_seconds = 172800;

CREATE TABLE goid (
    goid text PRIMARY KEY,
    id text,
    objects map<text, frozen<tuple<int, bigint>>>,
    resgrp set<int>,
    isOpen boolean
) WITH compaction = {'class': 'LeveledCompactionStrategy'}
    AND gc_grace_seconds = 172800;

CREATE TABLE locks (
    id text PRIMARY KEY,
    owner text
) WITH compaction = {'class': 'LeveledCompactionStrategy'}
    AND gc_grace_seconds = 90
    AND default_time_to_live = 90;