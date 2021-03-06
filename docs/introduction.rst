Introduction
============

Grouperfish is built to perform text clustering for `Firefox Input`_.
Due to its generic nature, it also serves as a testbed to prototype machine
learning algorithms.

.. _Firefox Input: http://input.mozilla.com

How does it work?
-----------------

Grouperfish is a *document transformation system*, for high throughput
applications.

Roughly summarized:

* users put *documents* into Grouperfish using a REST interface

* *transformations* are performed on one or several subsets of these documents.

* *results* can be retrieved by users over the REST interface

* all components are distributed for high volume applications


What can be done?
"""""""""""""""""

Assume a scenario where a steady stream of documents is generated.
For example:

* user feedback
* software crash reports
* twitter messages

Now, these documents can be processed to make them more useful.
For example:

* clustering (grouping related documents together, detecting common topics)
* classification (associating documents with predefined categories including
  spam)
* trending (identifying new topics over time).


Vocabulary
----------

Grouperfish users can assume one of three roles (or any combination thereof):

Document Producer
    Some user (usually another piece of software) that will
    put documents into the System.

Result Consumer
    Some user/software that gets the generated results.

Admin
    A user who configures which subsets of documents to transform, but also
    how and when to do that.


