package com.baeldung;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.project.MavenProject;
import org.apache.maven.model.Dependency;
import java.util.List;


// A mojo has to implement the Mojo interface. 
// In our case, well extend from AbstractMojo so well only have to implement the execute method

// As we can see, dependency-counter is the name of the goal. 
// On the other hand, weve attached it to the compile phase by default so we wont necessarily have to specify a phase when using this goal.
@Mojo(name = "dependency-counter", defaultPhase = LifecyclePhase.COMPILE)
public class DependencyCounterMojo extends AbstractMojo {
	
	
	
	// ReadOnly parameter, cant be configured by the user and it is injected by Maven
	@Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;
	
	//  Scope: determine when a dependency is included in a classpath.
	// Scope of the dependency we want to count, it allow us to set this property via the command line or a POM property
	@Parameter(property = "scope")
	String scope;
	
	
	// Implement the execute method and count the number of dependencies of the project
    public void execute()
        throws MojoExecutionException, MojoFailureException
    {
		List<Dependency> dependencies = project.getDependencies();
		long numDependencies = dependencies.stream().filter(d -> (scope == null || scope.isEmpty()) || scope.equals(d.getScope())).count();
		// getLog() method provide acces to the Maven log, the AbstractMojo abstract class handles its lifecycle
		getLog().info("Number of dependencies: "+ numDependencies);
    }
}
