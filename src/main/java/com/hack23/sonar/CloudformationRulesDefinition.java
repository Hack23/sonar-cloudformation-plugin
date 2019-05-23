package com.hack23.sonar;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionXmlLoader;
import org.sonar.api.server.rule.RulesDefinition.NewRule;
import org.sonar.api.server.rule.RulesDefinition.OwaspTop10;

public class CloudformationRulesDefinition implements RulesDefinition {

	  private static final String PATH_TO_RULES_XML = "/cloudformation-rules.xml";

	  protected static final String KEY = "cfn";
	  protected static final String NAME = "Cloudformation";

	  public static final String REPO_KEY = CloudformationLanguage.KEY + "-" + KEY;
	  protected static final String REPO_NAME = CloudformationLanguage.KEY + "-" + NAME;

	  protected String rulesDefinitionFilePath() {
	    return PATH_TO_RULES_XML;
	  }

	  private void defineRulesForLanguage(Context context, String repositoryKey, String repositoryName, String languageKey) {
	    NewRepository repository = context.createRepository(repositoryKey, languageKey).setName(repositoryName);

	    InputStream rulesXml = this.getClass().getResourceAsStream(rulesDefinitionFilePath());
	    if (rulesXml != null) {
	      RulesDefinitionXmlLoader rulesLoader = new RulesDefinitionXmlLoader();
	      rulesLoader.load(repository, rulesXml, StandardCharsets.UTF_8.name());
	      
	      
	      for (NewRule newRule : repository.rules()) {
	      	
	  		try {
	  			final Set<String> tags = (Set<String>) FieldUtils.readField(newRule, "tags", true);
	  			for (String tag : tags) {
	  				
	  				if (tag.contains("cweid-")) {
	  					newRule.addCwe(Integer.parseInt(tag.replace("cweid-", "")));					
	  				}
	  		    	
	  				if (tag.contains("owasp-")) {
	  					newRule.addOwaspTop10(OwaspTop10.valueOf(tag.replace("owasp-", "").toUpperCase()));				
	  				}
	  			}
	  		} catch (IllegalAccessException e) {
	  			//LOGGER.warn("Problem parsing security tags",e);
	  		} 
	      }
	      }
	    repository.done();
	    
	  }
	  

	  @Override
	  public void define(Context context) {
	    defineRulesForLanguage(context, REPO_KEY, REPO_NAME, CloudformationLanguage.KEY);
	  }

	
}
