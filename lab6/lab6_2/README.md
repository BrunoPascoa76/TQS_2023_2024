# Code analysis report

## First run

### Coverage

58.5% (mostly because I didn't fully test the Equals and HashCode methods generated by Lombok)

### Duplication

0%

### Issues

* Security:
  * 0 Issues
* Reliability:
  * 1 medium issue (use constructor injection instead of autowired)
* Maintainability:
  * 1 high issue (empty test class I forgot to delete)
  * 1 medium issue (constructor injection)
  * 4 low issues (unused imports and removing public modifier)
  * Debt: 22min

## Second run

### Changes

All issues resolved