// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.backend.core.web.request.AbstractAPIRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.util.Objects;
import java.util.Optional;

import static org.apache.hc.core5.http.URIScheme.HTTPS;

public abstract class AbstractFixerAPIRequest extends AbstractAPIRequest {

    private static final HttpHost HOST = new HttpHost(HTTPS.getId(), "data.fixer.io");

    private final NameValuePair accessKeyParameter;

    protected AbstractFixerAPIRequest(@Nonnull String accessKey) {
        this.accessKeyParameter = new BasicNameValuePair("access_key", accessKey);
    }

    @Override
    protected final @Nonnull HttpHost getHost() {
        return HOST;
    }

    @Override
    protected final Optional<String> getRootPathSegment() {
        return Optional.of("api");
    }

    @Override
    protected final Optional<NameValuePair> getAccessKeyParameter() {
        return Optional.of(accessKeyParameter);
    }

    @Override
    public int hashCode() { // Deliberately non-final
        return Objects.hashCode(accessKeyParameter);
    }
}
