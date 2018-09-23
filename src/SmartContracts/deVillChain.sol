pragma solidity ^0.4.25;

contract deVillChain {
    address contractOwner;//owner of the contract

    uint256 countUsers; // count for mapping purposes
    mapping (uint256 => address) mapUsers; // all users, mapped using countUsers

    //mapping (address => mapping(uint256 => struct))


    struct bareSource
    {
        string thisHash;
        string hashofFile;
        uint64 timestamp;
        //address poster; // captured with map i.e. when adding
        string originalUrl;
        uint256 countAdditional;
        mapping (uint256 => supplemtarySource) mapAdditional;
    }

    struct supplementarySource //only if hashoffiles are identical
    {
        uint64 timestamp;
        string additionalUrl;
        string hashoffile;
    }

    function deVillChain(){

    }
}
