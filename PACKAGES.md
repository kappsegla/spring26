Classes are put in their resource (noun) package.
books, events, orders, products, users...

The search functionality is a verb and can be put in either:
1. Functional Domain, its own package search
2. Put all non-crud functionality in its own package, queries, explore
3. If the search belongs to a resource put it in that package.
