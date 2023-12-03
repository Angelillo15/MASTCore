package com.nookure.mast.api.webhook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nookure.mast.api.webhook.config.JsonConfig;
import com.nookure.mast.api.webhook.serializer.DateSerializer;
import com.nookure.mast.api.webhook.serializer.EmbedSerializer;
import com.nookure.mast.api.webhook.serializer.WebHookSerializer;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.requireNonNull;

/**
 * An object capable of sending requests to publish WebHooks in a given Discord channel.
 */
@SuppressWarnings("unused")
public final class WebHookClient {
  private static final String BASE_URL = "https://discord.com/api/webhooks/%s/%s";
  private final URI webhookURL;
  private final HttpClient httpClient = HttpClient.newHttpClient();
  private final Gson gson = new GsonBuilder()
      .registerTypeAdapter(OffsetDateTime.class, new DateSerializer())
      .registerTypeAdapter(Embed.class, new EmbedSerializer())
      .registerTypeAdapter(WebHook.class, new WebHookSerializer())
      .create();

  private WebHookClient(final URI url) {
    this.webhookURL = url;
  }

  /**
   * Creates a new WebHookClient based on a {@link URI}.
   *
   * @param uri the webhook url provided
   * @return a new WebHookClient
   * @throws IllegalArgumentException if the provided URL is invalid
   */
  public static WebHookClient fromURL(final @NotNull String uri) {
    requireNonNull(uri, "uri");
    try {
      final URI webhookURI = new URI(uri);
      return new WebHookClient(webhookURI);
    } catch (final URISyntaxException e) {
      throw new IllegalArgumentException("Invalid URI provided", e);
    }
  }

  /**
   * Creates a new WebHookClient based on its id and token.
   *
   * @param id    the webhook id
   * @param token the webhook token
   * @return a new WebHookClient
   * @throws IllegalArgumentException if the provided id or token are is invalid
   * @see #fromURL(String)
   */
  public static WebHookClient from(final @NotNull String id, final @NotNull String token) {
    requireNonNull(id, "id");
    requireNonNull(token, "token");

    return fromURL(BASE_URL.formatted(id, token));
  }

  /**
   * Sends the specified WebHook.
   *
   * @param webHook the webhook to send
   * @return a CompletableFuture with the result of this request
   */
  public CompletableFuture<HttpResponse<String>> sendWebHook(final @NotNull WebHook webHook) {
    requireNonNull(webHook, "webhook");
    final String json = gson.toJson(webHook);

    return sendRequest(json);
  }

  public CompletableFuture<HttpResponse<String>> sendWebHook(final @NotNull JsonConfig config) {
    requireNonNull(config, "config");

    return sendRequest(config.toString());
  }

  public CompletableFuture<HttpResponse<String>> sendWebHook(final @NotNull JsonConfig config, @NotNull Replacer... replace) {
    requireNonNull(config, "config");
    AtomicReference<String> json = new AtomicReference<>(config.toString());

    Arrays.stream(replace).forEach(replacer -> {
      if (json.get().contains("{{ " + replacer.key() + " }}")) {
        json.set(json.get().replace("{{ " + replacer.key() + " }}", replacer.value()));
      }
    });

    return sendRequest(json.get());
  }

  private CompletableFuture<HttpResponse<String>> sendRequest(final String json) {
    final HttpRequest request = HttpRequest.newBuilder()
        .uri(this.webhookURL)
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
        .build();

    return this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
  }
}