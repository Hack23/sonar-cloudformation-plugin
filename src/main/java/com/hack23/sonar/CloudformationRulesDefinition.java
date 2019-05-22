package com.hack23.sonar;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionXmlLoader;

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
	    }

	    repository.done();
	  }

	  @Override
	  public void define(Context context) {
	    defineRulesForLanguage(context, REPO_KEY, REPO_NAME, CloudformationLanguage.KEY);
	  }

	
}
