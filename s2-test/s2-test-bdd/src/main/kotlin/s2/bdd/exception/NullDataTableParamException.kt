package s2.bdd.exception

class NullDataTableParamException(
    param: String
): IllegalDataTableParamException(param, "Should not be null")
