package com.mattmalec.pterodactyl4j.application.managers;

import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.EnvironmentValue;
import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;
import com.mattmalec.pterodactyl4j.application.entities.Allocation;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationEgg;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationUser;
import com.mattmalec.pterodactyl4j.application.entities.impl.ApplicationServerImpl;
import com.mattmalec.pterodactyl4j.application.entities.impl.PteroApplicationImpl;
import com.mattmalec.pterodactyl4j.requests.Route;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ServerManager {

	private ApplicationServer server;
	private PteroApplicationImpl impl;

	public ServerManager(ApplicationServer server, PteroApplicationImpl impl) {
		this.server = server;
		this.impl = impl;
	}

	public PteroAction<ApplicationServer> setName(String name) {
		return PteroActionImpl.onExecute(() -> {
			JSONObject obj = new JSONObject()
					.put("name", name)
					.put("description", server.getDescription())
					.put("user", server.getOwner().get().orElseGet(() -> server.getOwner().retrieve().execute()).getId());
			return new PteroActionImpl<ApplicationServer>(impl.getPteroApi(),
					Route.Servers.UPDATE_SERVER_DETAILS.compile(server.getId()), PteroActionImpl.getRequestBody(obj),
					(response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	public PteroAction<ApplicationServer> setOwner(ApplicationUser user) {
		JSONObject obj = new JSONObject()
				.put("name", server.getName())
				.put("description", server.getDescription())
				.put("user", user.getId());
		return new PteroActionImpl<>(impl.getPteroApi(), Route.Servers.UPDATE_SERVER_DETAILS.compile(server.getId()),
				PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject()));
	}

	public PteroAction<ApplicationServer> setDescription(String description) {
		return PteroActionImpl.onExecute(() -> {
			JSONObject obj = new JSONObject()
					.put("name", server.getName())
					.put("description", description)
					.put("user", server.getOwner().get().orElseGet(() -> server.getOwner().retrieve().execute()).getId());
			return new PteroActionImpl<ApplicationServer>(impl.getPteroApi(),
					Route.Servers.UPDATE_SERVER_DETAILS.compile(server.getId()), PteroActionImpl.getRequestBody(obj),
					(response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	public PteroAction<ApplicationServer> setAllocation(Allocation allocation) {
		JSONObject obj = new JSONObject()
				.put("memory", server.getLimits().getMemory())
				.put("swap", server.getLimits().getSwap())
				.put("io", server.getLimits().getIO())
				.put("cpu", server.getLimits().getCPU())
				.put("disk", server.getLimits().getDisk())
				.put("threads", server.getLimits().getThreads());
		JSONObject allocations = new JSONObject()
				.put("default", allocation.getId());
		obj.put("allocation", allocations);
		JSONObject featureLimits = new JSONObject()
				.put("databases", server.getFeatureLimits().getDatabases())
				.put("allocations", server.getFeatureLimits().getAllocations())
				.put("backups", server.getFeatureLimits().getBackups());
		obj.put("feature_limits", featureLimits);
		return new PteroActionImpl<>(impl.getPteroApi(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
				PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject()));
	}

	public PteroAction<ApplicationServer> setMemory(long amount, DataType dataType) {
		return PteroActionImpl.onExecute(() ->
		{
			long trueAmount;
			if (dataType == DataType.MB)
				trueAmount = amount;
			else
				trueAmount = amount * dataType.getMbValue();

			JSONObject obj = new JSONObject()
					.put("memory", trueAmount)
					.put("swap", server.getLimits().getSwap())
					.put("io", server.getLimits().getIO())
					.put("cpu", server.getLimits().getCPU())
					.put("disk", server.getLimits().getDisk())
					.put("threads", server.getLimits().getThreads());
			JSONObject allocations = new JSONObject()
					.put("default", server.getDefaultAllocation().get().orElseGet(() -> server.getDefaultAllocation().retrieve().execute()).getId());
			obj.put("allocation", allocations);
			JSONObject featureLimits = new JSONObject()
					.put("databases", server.getFeatureLimits().getDatabases())
					.put("allocations", server.getFeatureLimits().getAllocations())
					.put("backups", server.getFeatureLimits().getBackups());
			obj.put("feature_limits", featureLimits);

			return new PteroActionImpl<ApplicationServer>(impl.getPteroApi(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	public PteroAction<ApplicationServer> setSwap(long amount, DataType dataType) {
		return PteroActionImpl.onExecute(() ->
		{
			long trueAmount;
			if (dataType == DataType.MB)
				trueAmount = amount;
			else
				trueAmount = amount * dataType.getMbValue();

			JSONObject obj = new JSONObject()
					.put("memory", server.getLimits().getMemory())
					.put("swap", trueAmount)
					.put("io", server.getLimits().getIO())
					.put("cpu", server.getLimits().getCPU())
					.put("disk", server.getLimits().getDisk())
					.put("threads", server.getLimits().getThreads());
			JSONObject allocations = new JSONObject()
					.put("default", server.getDefaultAllocation().get().orElseGet(() -> server.getDefaultAllocation().retrieve().execute()).getId());
			obj.put("allocation", allocations);
			JSONObject featureLimits = new JSONObject()
					.put("databases", server.getFeatureLimits().getDatabases())
					.put("allocations", server.getFeatureLimits().getAllocations())
					.put("backups", server.getFeatureLimits().getBackups());
			obj.put("feature_limits", featureLimits);

			return new PteroActionImpl<ApplicationServer>(impl.getPteroApi(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	public PteroAction<ApplicationServer> setIO(long amount) {
		return PteroActionImpl.onExecute(() ->
		{
			JSONObject obj = new JSONObject()
					.put("memory", server.getLimits().getMemory())
					.put("swap", server.getLimits().getSwap())
					.put("io", amount)
					.put("cpu", server.getLimits().getCPU())
					.put("disk", server.getLimits().getDisk())
					.put("threads", server.getLimits().getThreads());
			JSONObject allocations = new JSONObject()
					.put("default", server.getDefaultAllocation().get().orElseGet(() -> server.getDefaultAllocation().retrieve().execute()).getId());
			obj.put("allocation", allocations);
			JSONObject featureLimits = new JSONObject()
					.put("databases", server.getFeatureLimits().getDatabases())
					.put("allocations", server.getFeatureLimits().getAllocations())
					.put("backups", server.getFeatureLimits().getBackups());
			obj.put("feature_limits", featureLimits);

			return new PteroActionImpl<ApplicationServer>(impl.getPteroApi(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	public PteroAction<ApplicationServer> setCPU(long amount) {
		return PteroActionImpl.onExecute(() ->
		{
			JSONObject obj = new JSONObject()
					.put("memory", server.getLimits().getMemory())
					.put("swap", server.getLimits().getSwap())
					.put("io", server.getLimits().getIO())
					.put("cpu", amount)
					.put("disk", server.getLimits().getDisk())
					.put("threads", server.getLimits().getThreads());
			JSONObject allocations = new JSONObject()
					.put("default", server.getDefaultAllocation().get().orElseGet(() -> server.getDefaultAllocation().retrieve().execute()).getId());
			obj.put("allocation", allocations);
			JSONObject featureLimits = new JSONObject()
					.put("databases", server.getFeatureLimits().getDatabases())
					.put("allocations", server.getFeatureLimits().getAllocations())
					.put("backups", server.getFeatureLimits().getBackups());
			obj.put("feature_limits", featureLimits);

			return new PteroActionImpl<ApplicationServer>(impl.getPteroApi(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	public PteroAction<ApplicationServer> setThreads(String cores) {
		return PteroActionImpl.onExecute(() ->
		{
			JSONObject obj = new JSONObject()
					.put("memory", server.getLimits().getMemory())
					.put("swap", server.getLimits().getSwap())
					.put("io", server.getLimits().getIO())
					.put("cpu", server.getLimits().getCPU())
					.put("disk", server.getLimits().getDisk())
					.put("threads", cores);
			JSONObject allocations = new JSONObject()
					.put("default", server.getDefaultAllocation().get().orElseGet(() -> server.getDefaultAllocation().retrieve().execute()).getId());
			obj.put("allocation", allocations);
			JSONObject featureLimits = new JSONObject()
					.put("databases", server.getFeatureLimits().getDatabases())
					.put("allocations", server.getFeatureLimits().getAllocations())
					.put("backups", server.getFeatureLimits().getBackups());
			obj.put("feature_limits", featureLimits);

			return new PteroActionImpl<ApplicationServer>(impl.getPteroApi(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	public PteroAction<ApplicationServer> setDisk(long amount, DataType dataType) {
		return PteroActionImpl.onExecute(() ->
		{
			long trueAmount;
			if (dataType == DataType.MB)
				trueAmount = amount;
			else
				trueAmount = amount * dataType.getMbValue();

			JSONObject obj = new JSONObject()
					.put("memory", server.getLimits().getMemory())
					.put("swap", server.getLimits().getSwap())
					.put("io", server.getLimits().getIO())
					.put("cpu", server.getLimits().getCPU())
					.put("disk", trueAmount)
					.put("threads", server.getLimits().getThreads());
			JSONObject allocations = new JSONObject()
					.put("default", server.getDefaultAllocation().get().orElseGet(() -> server.getDefaultAllocation().retrieve().execute()).getId());
			obj.put("allocation", allocations);
			JSONObject featureLimits = new JSONObject()
					.put("databases", server.getFeatureLimits().getDatabases())
					.put("allocations", server.getFeatureLimits().getAllocations())
					.put("backups", server.getFeatureLimits().getBackups());
			obj.put("feature_limits", featureLimits);

			return new PteroActionImpl<ApplicationServer>(impl.getPteroApi(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	public PteroAction<ApplicationServer> setAllowedDatabases(int amount) {
		return PteroActionImpl.onExecute(() ->
		{
			JSONObject obj = new JSONObject()
					.put("memory", server.getLimits().getMemory())
					.put("swap", server.getLimits().getSwap())
					.put("io", server.getLimits().getIO())
					.put("cpu", server.getLimits().getCPU())
					.put("disk", server.getLimits().getDisk())
					.put("threads", server.getLimits().getThreads());
			JSONObject allocations = new JSONObject()
					.put("default", server.getDefaultAllocation().get().orElseGet(() -> server.getDefaultAllocation().retrieve().execute()).getId());
			obj.put("allocation", allocations);
			JSONObject featureLimits = new JSONObject()
					.put("databases", amount)
					.put("allocations", server.getFeatureLimits().getAllocations())
					.put("backups", server.getFeatureLimits().getBackups());
			obj.put("feature_limits", featureLimits);

			return new PteroActionImpl<ApplicationServer>(impl.getPteroApi(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	public PteroAction<ApplicationServer> setAllowedAllocations(int amount) {
		return PteroActionImpl.onExecute(() ->
		{
			JSONObject obj = new JSONObject()
					.put("memory", server.getLimits().getMemory())
					.put("swap", server.getLimits().getSwap())
					.put("io", server.getLimits().getIO())
					.put("cpu", server.getLimits().getCPU())
					.put("disk", server.getLimits().getDisk())
					.put("threads", server.getLimits().getThreads());
			JSONObject allocations = new JSONObject()
					.put("default", server.getDefaultAllocation().get().orElseGet(() -> server.getDefaultAllocation().retrieve().execute()).getId());
			obj.put("allocation", allocations);
			JSONObject featureLimits = new JSONObject()
					.put("databases", server.getFeatureLimits().getDatabases())
					.put("allocations", amount)
					.put("backups", server.getFeatureLimits().getBackups());
			obj.put("feature_limits", featureLimits);

			return new PteroActionImpl<ApplicationServer>(impl.getPteroApi(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	public PteroAction<ApplicationServer> setAllowedBackups(int amount) {
		return PteroActionImpl.onExecute(() ->
		{
			JSONObject obj = new JSONObject()
					.put("memory", server.getLimits().getMemory())
					.put("swap", server.getLimits().getSwap())
					.put("io", server.getLimits().getIO())
					.put("cpu", server.getLimits().getCPU())
					.put("disk", server.getLimits().getDisk())
					.put("threads", server.getLimits().getThreads());
			JSONObject allocations = new JSONObject()
					.put("default", server.getDefaultAllocation().get().orElseGet(() -> server.getDefaultAllocation().retrieve().execute()).getId());
			obj.put("allocation", allocations);
			JSONObject featureLimits = new JSONObject()
					.put("databases", server.getFeatureLimits().getDatabases())
					.put("allocations", server.getFeatureLimits().getAllocations())
					.put("backups", amount);
			obj.put("feature_limits", featureLimits);

			return new PteroActionImpl<ApplicationServer>(impl.getPteroApi(), Route.Servers.UPDATE_SERVER_BUILD.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj), (response, request) -> new ApplicationServerImpl(impl, response.getObject())).execute();
		});
	}

	public PteroAction<Void> setStartupCommand(String command) {
		return PteroActionImpl.onExecute(() ->
		{
			JSONObject obj = new JSONObject()
					.put("startup", command)
					.put("environment", server.getContainer().getEnvironment().keySet())
					.put("egg", server.getEgg().retrieve().execute().getId())
					.put("image", server.getContainer().getImage());
			return new PteroActionImpl<Void>(impl.getPteroApi(),
					Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj)).execute();
		});
	}

	public PteroAction<Void> setEnvironment(Map<String, EnvironmentValue<?>> environment) {
		Map<String, Object> env = new HashMap<>();
		environment.forEach((k, v) -> env.put(k, v.get().orElse(null)));
		return PteroActionImpl.onExecute(() ->
		{
			JSONObject obj = new JSONObject()
					.put("startup", server.getContainer().getStartupCommand())
					.put("environment", env)
					.put("egg", server.getEgg().retrieve().execute().getId())
					.put("image", server.getContainer().getImage());
			return new PteroActionImpl<Void>(impl.getPteroApi(),
					Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj)).execute();
		});
	}

	public PteroAction<Void> setEgg(ApplicationEgg egg) {
		JSONObject obj = new JSONObject()
				.put("startup", server.getContainer().getStartupCommand())
				.put("environment", server.getContainer().getEnvironment().keySet())
				.put("egg", egg.getId())
				.put("image", server.getContainer().getImage());
		return PteroActionImpl.onRequestExecute(impl.getPteroApi(),
				Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()),
				PteroActionImpl.getRequestBody(obj));

	}

	public PteroAction<Void> setImage(String dockerImage) {
		return PteroActionImpl.onExecute(() ->
		{
			JSONObject obj = new JSONObject()
					.put("startup", server.getContainer().getStartupCommand())
					.put("environment", server.getContainer().getEnvironment().keySet())
					.put("egg", server.getEgg().retrieve().execute().getId())
					.put("image", dockerImage);
			return new PteroActionImpl<Void>(impl.getPteroApi(),
					Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj)).execute();
		});
	}

	public PteroAction<Void> setSkipScripts(boolean skipScripts) {
		return PteroActionImpl.onExecute(() ->
        {
				JSONObject obj = new JSONObject()
						.put("startup", server.getContainer().getStartupCommand())
						.put("environment",  server.getContainer().getEnvironment().keySet())
						.put("egg", server.getEgg().retrieve().execute().getId())
						.put("image", server.getContainer().getImage())
						.put("skip_scripts", skipScripts);
			return new PteroActionImpl<Void>(impl.getPteroApi(),
					Route.Servers.UPDATE_SERVER_STARTUP.compile(server.getId()),
					PteroActionImpl.getRequestBody(obj)).execute();
        });
	}
}
