@GetMapping(value = "/communityEmail")
    public List<CommunityEmail> getCommunityEmail(@RequestParam("city") String city) throws Exception {
@GetMapping(value = "/flood")
    public MappingJacksonValue getFloodListHome(@RequestParam("stations") List<String> stations) throws Exception {
mockMvc.perform(get("/communityEmail?city=citytest")
mockMvc.perform(get("/personInfo?firstName=firstnametest&lastName=lastnametest")
