# Code analysis report

## 1st run (overall code)

The code passed the quality gate at first try.

### Issues

* Security:
  * No Issues
* Reliability:
  * No Issues
* Maintainability:
  * 10 medium severity issues:
    * Invoke methods only conditionally when using .format() on logs. Should move .format() code to toString() so it automatically formats
    * Loop counters in the loop body when doing i++ on for loops. Should change for loops for while loops
    * Using AssertEquals/notEquals instead of AssertTrue/False.
  * 15 low issues:
    * Mostly to remove unneeded "public"
  * Total debt: 1h22min (I am unsure whether or not this is realistic as these issues have minimal impact on the overall code)
  
### Coverage

86.8% code coverage (which isn't surprising as increasing code coverage was the main objective of lab1_2)

### Duplications

0%

### Security hotspots

* Random should be cryptographically secure (solving this issue is not needed since this instance of randomness has no security implications, but could be solved by using a different randomness generator).
